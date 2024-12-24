package com.example.list_4pm2_2425

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.list_4pm2_2425.converters.DateConverter
import com.example.list_4pm2_2425.daos.FacultyDAO
import com.example.list_4pm2_2425.daos.GroupDAO
import com.example.list_4pm2_2425.daos.StudentDAO
import com.example.list_4pm2_2425.data.Faculty
import com.example.list_4pm2_2425.data.Group
import com.example.list_4pm2_2425.data.Student

@Database(entities = [(Student::class), (Group::class), (Faculty::class)], version = 1)
@TypeConverters(DateConverter::class)
abstract class UniversityDatabase : RoomDatabase() {
    abstract fun studentDAO(): StudentDAO
    abstract fun facultyDAO(): FacultyDAO
    abstract fun groupDAO(): GroupDAO

    companion object {
        private var INSTANCE: UniversityDatabase? = null
        fun getInstance(context: Context): UniversityDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UniversityDatabase::class.java,
                        "app_db"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}