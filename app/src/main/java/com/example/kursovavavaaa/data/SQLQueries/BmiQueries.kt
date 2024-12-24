package com.example.kursovavavaaa.data.SQLQueries

class BmiQueries {
    // Create a new BMI table
    private val createBmiTableQuery = "CREATE TABLE bmi (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "value REAL," +
            "date TEXT" +
            ");"

    // Return the create BMI table query
    fun getCreateBmiTableQuery(): String {
        return createBmiTableQuery
    }

    // Delete the BMI table
    private val deleteBmiTableQuery = "DROP TABLE IF EXISTS bmi;"

    // Return the delete BMI table query
    fun getDeleteBmiTableQuery(): String {
        return deleteBmiTableQuery
    }

}