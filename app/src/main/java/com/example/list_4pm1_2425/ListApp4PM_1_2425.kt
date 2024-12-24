package com.example.list_4pm1_2425

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.list_4pm1_2425.repository.AppRepository

class ListApp4PM_1_2425: Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("ERROR", "SOMETHING WENT WRONG ON START")
        UniversityDatabase.getInstance(this)
        AppRepository.getInstance()
    }

    init {
        instance = this
    }

    companion object {
        private var instance: ListApp4PM_1_2425? = null

        val context
            get()= applicationContext()

        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}