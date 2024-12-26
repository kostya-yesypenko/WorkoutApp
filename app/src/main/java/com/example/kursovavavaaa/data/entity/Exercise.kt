package com.example.kursovavavaaa.data.entity

data class Exercise(
    val id: Int? = 9999,
    val name: String,
    val description: String,
    val type: String,
    val image: String,
    val points: Int,
    val reps: Int,
    val calories: Int,
)