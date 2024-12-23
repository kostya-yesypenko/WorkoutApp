package com.example.kursovavavaaa.data.SQLQueries

class ExerciseQueries {
    val createExerciseTableQuery = "CREATE TABLE exercise (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "description TEXT," +
            "type TEXT," +
            "difficulty TEXT," +
            "image TEXT" +
            ");"
}