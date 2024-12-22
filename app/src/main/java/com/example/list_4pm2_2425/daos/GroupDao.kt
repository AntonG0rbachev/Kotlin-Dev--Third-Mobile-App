package com.example.list_4pm2_2425.daos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.list_4pm2_2425.data.Faculty
import com.example.list_4pm2_2425.data.Group
import com.example.list_4pm2_2425.data.ListOfGroup
import com.example.list_4pm2_2425.data.Student

@Dao
interface GroupDao {
    @Query("SELECT * FROM groups")
    fun getGroups(): LiveData<List<Group>>

    @Insert
    fun addGroup(group: Group)

    @Update
    fun updateGroup(group: Group)

    @Update
    fun updateGroups(groups: List<Group>): Int

    @Query("DELETE FROM groups WHERE group_id = :id")
    fun deleteGroup(id: Long)
}