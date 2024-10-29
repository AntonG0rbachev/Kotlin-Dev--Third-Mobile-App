package com.example.thirdmobileapp.data

import java.util.UUID

data class Faculty(
    val id: UUID = UUID.randomUUID(),
    var name: String = ""
)
