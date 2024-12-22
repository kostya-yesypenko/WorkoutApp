package com.example.kursovavavaaa.data.entity

data class Exercise(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val type: String,
    val difficulty: List<Complexity>,
    val points: Int
)