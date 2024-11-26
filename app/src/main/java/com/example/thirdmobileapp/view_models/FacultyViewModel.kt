package com.example.thirdmobileapp.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.thirdmobileapp.data.Faculty
import com.example.thirdmobileapp.data.ListOfFaculty
import com.example.thirdmobileapp.repository.AppRepository

class FacultyViewModel : ViewModel() {
    var facultyList: MutableLiveData<ListOfFaculty?> = MutableLiveData()

    private var _faculty: Faculty? = null

    val faculty
        get() = _faculty

    private val facultyListObserver = Observer<ListOfFaculty?> {
        list -> facultyList.postValue(list)
    }

    init {
        AppRepository.getInstance().listOfFaculty.observeForever(facultyListObserver)
        AppRepository.getInstance().faculty.observeForever {
            _faculty = it
        }
    }

    fun deleteFaculty() {
        if (faculty != null)
            AppRepository.getInstance().deleteFaculty(faculty!!)
    }

    fun appendFaculty(facultyName: String) {
        val faculty = Faculty()
        faculty.name = facultyName
        AppRepository.getInstance().updateFaculty(faculty)
    }

    fun updateFaculty(facultyName: String) {
        if (_faculty != null) {
            _faculty!!.name = facultyName
            AppRepository.getInstance().updateFaculty(_faculty!!)
        }
    }

    fun setCurrentFaculty(faculty: Faculty) {
        AppRepository.getInstance().setCurrentFaculty(faculty)
    }
}