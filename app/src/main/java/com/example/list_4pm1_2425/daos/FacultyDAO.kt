package com.example.list_4pm1_2425.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.list_4pm1_2425.data.Faculty

@Dao
interface FacultyDAO {
    @Query("SELECT * FROM faculties")
    fun getFaculties(): LiveData<List<Faculty>>

    @Insert
    fun addFaculty(faculty: Faculty)

    @Update
    fun updateFaculty(faculty: Faculty)

    @Update
    fun updateFaculties(faculties: List<Faculty>): Int

    @Query("DELETE FROM faculties WHERE faculty_id = :id")
    fun deleteFaculty(id: Long)
}