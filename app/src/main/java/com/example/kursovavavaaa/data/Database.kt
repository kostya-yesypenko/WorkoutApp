package com.example.kursovavavaaa.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import com.example.kursovavavaaa.data.entity.User

// Database class
class Database(val context: Context, factory: CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {

    // Create database tables on first run
    override fun onCreate(db: SQLiteDatabase?) {
        if (db == null) return

        // Define the query (users, statistics, completed_exercises, exercises, complexities, completed_workouts, workouts, exercises_in_workout)
        val create_users: String = """
                CREATE TABLE users (
                    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    height REAL NOT NULL,
                    weight REAL NOT NULL,
                    gender TEXT NOT NULL,
                    age INTEGER NOT NULL,
                    statistics_id INTEGER, 
                    FOREIGN KEY (statistics_id) REFERENCES statistics(id)
                );
            """.trimIndent()

        db.execSQL(create_users)

        val create_statistic: String = """
            CREATE TABLE statistics (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 
                pointsOverall INTEGER NOT NULL
            );
         """.trimIndent()

        db.execSQL(create_statistic)

        val create_completed_exercises: String = """
            CREATE TABLE completed_exercises (  
                statistic_id INTEGER NOT NULL,  
                exercise_id INTEGER NOT NULL,
                FOREIGN KEY (statistic_id) REFERENCES statistics(id),
                FOREIGN KEY (exercise_id) REFERENCES exercises(id)
            );
        """.trimIndent()

        db.execSQL(create_completed_exercises)
            
        val create_exercises: String = """
            CREATE TABLE exercises (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 
                name TEXT NOT NULL, 
                description TEXT NOT NULL,  
                image TEXT NOT NULL, 
                type TEXT NOT NULL,
                complexity_id INTEGER NOT NULL,
                FOREIGN KEY (complexity_id) REFERENCES complexities(id)  
            );
        """.trimIndent()

        db.execSQL(create_exercises)

        val create_complexities: String = """
            CREATE TABLE complexities (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                reps INTEGER NOT NULL,
                points INTEGER NOT NULL,
                FOREIGN KEY (exercise_id) REFERENCES exercises(id)
            );
        """.trimIndent()


        db.execSQL(create_complexities)

        val create_completed_workouts: String = """
            CREATE TABLE completed_workouts (
                statistic_id INTEGER NOT NULL,
                workout_id INTEGER NOT NULL,  
                FOREIGN KEY (statistic_id) REFERENCES statistics(id),
                FOREIGN KEY (workout_id) REFERENCES workouts(id)
            );
        """.trimIndent()

        db.execSQL(create_completed_workouts)

        val create_workouts: String = """
            CREATE TABLE workouts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,  
                name TEXT NOT NULL,  
                description TEXT NOT NULL,
                image TEXT NOT NULL,
                type TEXT NOT NULL
            );
        """.trimIndent()

        db.execSQL(create_workouts)

        val create_exercises_in_workout: String = """
            CREATE TABLE exercises_in_workout (
                workout_id INTEGER, 
                exercise_id INTEGER, 
                PRIMARY KEY (workout_id, exercise_id), 
                FOREIGN KEY (workout_id) REFERENCES workouts(id), 
                FOREIGN KEY (exercise_id) REFERENCES exercises(id) 
            );
        """.trimIndent()

        // Execute the query
        db.execSQL(create_exercises_in_workout)

        // Create empty user
        createUser()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    // Create empty user
    fun createUser(): Boolean {
        // Get the user
        val user: User? = getUser() ?: null

        // If user is not null, return false
        if (user != null) return false

        // Define the values
        val values = ContentValues()

        // Put the values
        values.put("name", "")
        values.put("height", 0.0)
        values.put("weight", 0.0)
        values.put("gender", "")
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