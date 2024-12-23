package com.example.kursovavavaaa.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.kursovavavaaa.data.SQLQueries.UsersQueries
import com.example.kursovavavaaa.data.entity.User

// Database class
class Database(val context: Context, factory: CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {

    // Create database tables on first run
    override fun onCreate(db: SQLiteDatabase?) {
        // Return if database is does not exist
        if (db == null) return

        // Create the users table
        val createUserTable = UsersQueries().createUsersTable()
        executeQuery(db, createUserTable)

    }

    // Update database actions
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // Delete the users table
        val deleteUserTable = UsersQueries().deleteUsersTable()
        executeQuery(db!!, deleteUserTable)
    }

    // Create the users table
    private fun executeQuery(db: SQLiteDatabase, query: String): Boolean {
        // Try to execute the query
        try {
            // Execute the query
            db.execSQL(query)
        }
        // Catch errors and display them
        catch (e: Exception) {
            // Show error message
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            // return false if unsuccessful
            return false
        }

        // return true if successful
        return true
    }


    // Create empty user
    fun createUser(): Boolean {
        // Get the user
        val user: User? = getUser()

        // If user is not null, return false
        if (user != null) return false

        // Define the values
        val values = ContentValues()

        // Put the values
        values.put("name", "Користувач")
        values.put("height", 0.0)
        values.put("weight", 0.0)
        values.put("gender", "Не вказано")
        values.put("age", 0)

        // Get the database
        val db = writableDatabase

        // Insert the values
        db.insert("users", null, values)

        return true
    }

    // Update the user
    fun updateUser(user: User) {
        // Define the values
        val values = ContentValues()


        // Put the values
        values.put("name", user.name)
        values.put("height", user.height)
        values.put("weight", user.weight)
        values.put("age", user.age)
        values.put("gender", user.gender)

        // Get the database
        val db = writableDatabase

        // Update the values
        val whereClause = "id = ?"
        val whereArgs = arrayOf(user.id.toString())
        db.update("users", values, whereClause, whereArgs)
    }


    // Get the user
    fun getUser(): User? {
        // Define the query
        val query = "SELECT * FROM users LIMIT 1"

        // Get the database
        val db = readableDatabase

        // Initialize User variable
        var user: User? = null

        // Get the cursor and handle it
        val cursor = db.rawQuery(query, null)

        try {
            // Check if cursor has data
            if (cursor.moveToFirst()) {
                // Get the user
                user = User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    height = cursor.getFloat(cursor.getColumnIndexOrThrow("height")),
                    weight = cursor.getFloat(cursor.getColumnIndexOrThrow("weight")),
                    gender = cursor.getString(cursor.getColumnIndexOrThrow("gender")),
                    age = cursor.getInt(cursor.getColumnIndexOrThrow("age"))
                )
            }
        } finally {
            // Close cursor
            cursor.close()
        }

        // Return the user (User)
        return user
    }

}