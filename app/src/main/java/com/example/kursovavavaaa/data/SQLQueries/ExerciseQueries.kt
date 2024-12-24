package com.example.kursovavavaaa.data.SQLQueries

class ExerciseQueries {
    // Create the exercise table query
    private val createExerciseTableQuery = "CREATE TABLE exercise (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "description TEXT," +
            "type TEXT," +
            "image TEXT," +
            "points INTEGER," +
            "reps INTEGER," +
            "calories INTEGER" +
            ");"

    // Return the create exercise query
    fun getCreateExerciseTableQuery(): String {
        return createExerciseTableQuery
    }

    // Delete the exercise table query
    private val deleteExerciseTableQuery = "DROP TABLE IF EXISTS exercise;"

    // Return the delete exercise query
    fun getDeleteExerciseTableQuery(): String {
        return deleteExerciseTableQuery
    }
}