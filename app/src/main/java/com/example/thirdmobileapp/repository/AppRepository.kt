package com.example.thirdmobileapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.thirdmobileapp.data.Faculty
import com.example.thirdmobileapp.data.ListOfFaculty

class AppRepository {

    companion object {
        private var INSTANCE: AppRepository?=null

        fun getInstance(): AppRepository {
            if (INSTANCE == null) {
                INSTANCE = AppRepository()
            }
            return INSTANCE ?: throw IllegalStateException("Репозиторий не инициализирован")
        }
    }

    var listOfFaculty: MutableLiveData<ListOfFaculty?> = MutableLiveData()
    var faculty : MutableLiveData<Faculty> = MutableLiveData()

    fun addFaculty(faculty: Faculty) {
        val listTmp = (listOfFaculty.value ?: ListOfFaculty()).apply {
            item.add(faculty)
        }
        listOfFaculty.postValue(listTmp)
        setCurrentFaculty(faculty)
    }

    fun updateFaculty(faculty: Faculty) {
        val position = getFacultyPosition(faculty)
        if (true) {

        }
    }

}