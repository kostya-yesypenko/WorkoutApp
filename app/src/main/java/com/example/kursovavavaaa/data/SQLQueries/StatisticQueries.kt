package com.example.kursovavavaaa.data.SQLQueries

// Statistic queries
class StatisticQueries {
    // Create the statistic table query
    private val createStatisticTableQuery =  "CREATE TABLE statistics (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "pointsOverall INT," +
            "BMI_id INT," +
            "completedExercises_id INT," +
            "completedWorkouts_id INT" +
            "FOREIGN KEY (completedExercises_id) REFERENCES exercise(id)" +
            "FOREIGN KEY (completedWorkouts_id) REFERENCES workout(id)" +
            ");"
}