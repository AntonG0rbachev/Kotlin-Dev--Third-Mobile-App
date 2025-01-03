package com.example.list_4pm1_2425.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.list_4pm1_2425.data.Student

@Dao
interface StudentDAO {
    @Query("SELECT * FROM students")
    fun getStudents(): LiveData<List<Student>>

    @Query("SELECT * FROM students WHERE student_id = :id")
    fun getStudent(id : Long) : Student

    @Insert
    fun addStudent(student: Student)

    @Query("SELECT EXISTS(SELECT * FROM students WHERE student_id = :id)")
    fun checkIfExist(id : Long) : Boolean


    @Update
    fun updateStudent(student: Student)

    @Update
    fun updateStudents(students: List<Student>): Int

    @Query("DELETE FROM students WHERE student_id = :id")
    fun deleteStudent(id: Long)
}