package com.example.kursovavavaaa.data
import java.time.LocalDateTime

data class User(
    val id: Int,
    val name: String,
    val weight: Float,
    val height: Float,
    val age: Int,
    val statistics: Statistics,
    val created: LocalDateTime
)