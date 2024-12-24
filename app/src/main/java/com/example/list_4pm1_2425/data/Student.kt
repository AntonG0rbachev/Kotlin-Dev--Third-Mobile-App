package com.example.list_4pm1_2425.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    var id: Long = 0,
    @ColumnInfo(name="last_name")
    var lastName: String? = null,
    @ColumnInfo(name="first_name")
    var firstName: String? = null,
    @ColumnInfo(name="middle_name")
    var middleName: String? = null,
    @ColumnInfo(name="birth_date")
    var birthDate: Date = Date(),
    @ColumnInfo(name="group_id")
    var groupID: Long? = null,
    var phone: String? = null,
    var sex: Int = 0
){
    val shortName
        get()=lastName+
                (if(firstName?.length != null) {" ${firstName?.subSequence(0,1)}. "} else "") +
                (if(middleName?.length != null) {" ${middleName?.subSequence(0,1)}. "} else "")

    public fun isEmpty() : Boolean {
        return firstName != null && lastName != null && middleName != null
    }
}


