package com.example.kursovavavaaa.data.entity

data class Statistic (
    val pointsOverall: Int,
    val BMI: BMI?,
    val completedExercises: List<CompletedExercise>?,
    //val completedWorkouts: List<Completed>?
)