package com.example.kursovavavaaa

import java.time.LocalDateTime

data class ExerciseItem(
    val name: String,
    val description: String,
    val image: String,
    val type: String,
    val difficulty: String,
    val time: Int,
    val points: Int
)
