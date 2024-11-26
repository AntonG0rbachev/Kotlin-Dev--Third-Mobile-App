package com.example.thirdmobileapp.data

import java.util.UUID

data class Group(
    val id : UUID = UUID.randomUUID(),
    var name : String = "",
    var facultyID : UUID ?= null
)
