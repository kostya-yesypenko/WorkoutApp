package com.example.kursovavavaaa.data.entity

data class Workout (
    val id: Int,
    val name: String,
    val complexity: Complexity,
    val exercises: List<Exercise>
)