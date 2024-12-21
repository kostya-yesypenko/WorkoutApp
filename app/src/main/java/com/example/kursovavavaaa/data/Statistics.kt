package com.example.kursovavavaaa.data

data class Statistics(
    val pointsOverall: Int,
    val completedExercises: List<Completed>,
    val completedWorkouts: List<Completed>
)