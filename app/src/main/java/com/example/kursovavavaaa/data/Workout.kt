package com.example.kursovavavaaa.data

data class Workout(
    val id: Int,
    val title: String,
    val description: String,
    val type: String,
    val exercises: List<Exercise>
)