package com.example.kursovavavaaa.data.SQLQueries

class DifficultyQuiries {
    // Create the difficulty table query
    private val createDifficultyTableQuery = "CREATE TABLE difficulty (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "time INTEGER," +
            "repsMult REAL," +
            "pointsMult REAL," +
            "caloriesMult REAL" +
            ");"

    // Return the create difficulty query
    fun getCreateDifficultyTableQuery(): String {
        return createDifficultyTableQuery
    }

    // Delete the difficulty table query
    private val deleteDifficultyTableQuery = "DROP TABLE IF EXISTS difficulty;"

    // Return the delete difficulty query
    fun getDeleteDifficultyTableQuery(): String {
        return deleteDifficultyTableQuery
    }
}