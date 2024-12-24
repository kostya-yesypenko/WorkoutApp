package com.example.kursovavavaaa.data.entity

data class User (
    val id: Int ? = 0,
    val name: String,
    val height: Float,
    val weight: Float,
    val gender: String,
    val age: Int,
    val statistics: Statistic? = null
)