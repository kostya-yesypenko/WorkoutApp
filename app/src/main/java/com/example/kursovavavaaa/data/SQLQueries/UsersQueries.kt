package com.example.kursovavavaaa.data.SQLQueries

// Users queries
class UsersQueries {
    // Create the users table query
    private val createUserTableQuery =  "CREATE TABLE users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "height REAL," +
            "weight REAL," +
            "gender TEXT," +
            "age INT" +
            ");"

    // Return the create user query
    fun createUsersTable(): String {
        return createUserTableQuery
    }

    // Delete the users table query
    private val deleteUserTableQuery = "DROP TABLE IF EXISTS users;"

    // Return the delete user query
    fun deleteUsersTable(): String {
        return deleteUserTableQuery
    }
}