package com.example.list_4pm2_2425.data

import java.util.UUID

data class Group(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var facultyID: UUID?=null
)
