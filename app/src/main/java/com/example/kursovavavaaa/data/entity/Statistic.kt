package com.example.kursovavavaaa.data.entity

data class Statistic (
    val pointsOverall: Int,
    val completedExercises: List<Completed>,
    val completedWorkouts: List<Completed>
)