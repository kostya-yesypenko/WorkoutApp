package com.example.kursovavavaaa.data

data class Exercise(
    val id: Int,
    val type: String,
    val title: String,
    val description: String,
    val level: Level,
    val calories: Int,
    val imgSrc: String
)