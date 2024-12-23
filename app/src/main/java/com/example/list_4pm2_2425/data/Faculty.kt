package com.example.list_4pm2_2425.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "faculties")
data class Faculty(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "faculty_id")
    var id: Long = 0,
    var name: String? = null
)
