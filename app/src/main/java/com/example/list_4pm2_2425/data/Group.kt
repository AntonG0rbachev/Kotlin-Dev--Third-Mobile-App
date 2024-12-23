package com.example.list_4pm2_2425.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "group_id")
    var id: Long = 0,
    var name: String? = null,
    @ColumnInfo(name = "faculty_id")
    var facultyID: Long? = null
)
