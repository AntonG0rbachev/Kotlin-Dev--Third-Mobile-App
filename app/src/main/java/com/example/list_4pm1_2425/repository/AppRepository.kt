package com.example.list_4pm1_2425.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.list_4pm1_2425.ListApp4PM_1_2425
import com.example.list_4pm1_2425.UniversityDatabase
import com.example.list_4pm1_2425.daos.FacultyDAO
import com.example.list_4pm1_2425.daos.GroupDAO
import com.example.list_4pm1_2425.daos.StudentDAO
import com.example.list_4pm1_2425.data.Faculty
import com.example.list_4pm1_2425.data.Group
import com.example.list_4pm1_2425.data.Student

class AppRepository(
    private val studentDAO: StudentDAO,
    private val facultyDAO: FacultyDAO,
    private val groupDAO: GroupDAO,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    companion object{
        private var INSTANCE: AppRepository?=null

        fun getInstance(): AppRepository{
            Log.e("WARNING", "ENTERED REPOSITORY")
            if(INSTANCE == null){
                val db = UniversityDatabase.getInstance(ListApp4PM_1_2425.context)
                val facultyDAO = db.facultyDAO()
                val groupDAO = db.groupDAO()
                val studentDAO = db.studentDAO()
                INSTANCE = AppRepository(studentDAO, facultyDAO, groupDAO)
            }
            return INSTANCE ?:
            throw IllegalStateException("Репозиторий не инициализирован")
        }
    }

    var facultyList: LiveData<List<Faculty>> = facultyDAO.getFaculties()
    var faculty: MutableLiveData<Faculty> = MutableLiveData()
    var groupList: LiveData<List<Group>> = groupDAO.getGroups()
    var group: MutableLiveData<Group> = MutableLiveData()
    var studentList: LiveData<List<Student>> = studentDAO.getStudents()
    var student: MutableLiveData<Student> = MutableLiveData()

    fun addFaculty(faculty: Faculty){
        Log.e("ADD", "ADD FACULTY ${faculty.name}")
        coroutineScope.launch(Dispatchers.IO) {
            facultyDAO.addFaculty(faculty)
        }
        setCurrentFaculty(faculty)
    }

    fun updateFaculty(faculty: Faculty){
        coroutineScope.launch(Dispatchers.IO) {
            facultyDAO.updateFaculty(faculty)
        }
    }

    fun deleteFaculty(faculty: Faculty){
        coroutineScope.launch(Dispatchers.IO) {
            facultyDAO.deleteFaculty(faculty.id)
        }
        setCurrentFaculty(0)
    }

    private fun getFacultyPosition(faculty: Faculty): Int = facultyList.value
        ?.indexOfFirst { it.id == faculty.id } ?: -1

    fun getFacultyPosition()= getFacultyPosition(faculty.value?: Faculty())

    private fun setCurrentFaculty(position: Int){
        if(facultyList.value==null || position<0 ||
            (facultyList.value?.size!!<=position))
            return
        setCurrentFaculty(facultyList.value!![position])
    }

    fun setCurrentFaculty(_faculty: Faculty){
        faculty.postValue(_faculty)
    }

    fun addGroup(group: Group){
        coroutineScope.launch(Dispatchers.IO) {
            groupDAO.addGroup(group)
        }
        setCurrentGroup(group)
    }

    private fun getGroupPosition(group: Group): Int = groupList.value?.indexOfFirst { it.id==group.id} ?:-1

    fun getGroupPosition()=getGroupPosition(group.value?: Group())

    private fun setCurrentGroup(position: Int){
        if(groupList.value==null || position<0 ||
            (groupList.value?.size!!<=position))
            return
        setCurrentGroup(groupList.value!![position])
    }

    fun setCurrentGroup(_group: Group){
        group.postValue(_group)
    }

    fun updateGroup(group: Group){
        coroutineScope.launch(Dispatchers.IO) {
            groupDAO.updateGroup(group)
        }
    }

    fun deleteGroup(group: Group){
        coroutineScope.launch(Dispatchers.IO) {
            groupDAO.deleteGroup(group.id)
        }
        setCurrentGroup(0)
    }

    val facultyGroups
        get()=groupList.value
            ?.filter{it.facultyID == (faculty.value?.id ?: 0)}
            ?.sortedBy { it.name } ?: listOf()

    fun getFacultyGroups(facultyID: Long) =
        groupList.value
            ?.filter{ it.facultyID == facultyID }
            ?.sortedBy { it.name } ?: listOf()

    fun addStudent(student: Student){
        coroutineScope.launch(Dispatchers.IO) {
            studentDAO.addStudent(student)
        }
        setCurrentStudent(student)
    }

    private fun getStudentPosition(student: Student): Int = studentList.value?.indexOfFirst {
        it.id==student.id} ?:-1

    fun getStudentPosition()=getStudentPosition(student.value?: Student())

    private fun setCurrentStudent(position: Int){
        if(studentList.value==null || position<0 ||
            (studentList.value?.size!!<=position))
            return
        setCurrentStudent(studentList.value!![position])
    }

    fun setCurrentStudent(_student: Student){
        student.postValue(_student)
    }

    fun getStudent(id: Long): Student{
        return studentDAO.getStudent(id)
    }

    fun updateStudent(student: Student){
        coroutineScope.launch(Dispatchers.IO) {
            if(studentDAO.checkIfExist(student.id))
                studentDAO.updateStudent(student)
            else
                studentDAO.addStudent(student)
        }
    }

    fun deleteStudent(student: Student){
        coroutineScope.launch(Dispatchers.IO) {
            studentDAO.deleteStudent(student.id)
        }
        setCurrentStudent(0)
    }

    val groupStudents
        get()=studentList.value
            ?.filter{it.groupID == (group.value?.id ?: 0)}
            ?.sortedBy { it.shortName } ?: listOf()

    fun getGroupStudents(groupID: Long) =
        (studentList.value
            ?.filter{ it.groupID == groupID }
            ?.sortedBy { it.shortName } ?: listOf())
}