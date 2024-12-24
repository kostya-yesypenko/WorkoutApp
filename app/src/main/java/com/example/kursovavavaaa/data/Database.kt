package com.example.kursovavavaaa.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.kursovavavaaa.data.SQLQueries.DifficultyQuiries
import com.example.kursovavavaaa.data.SQLQueries.ExerciseQueries
import com.example.kursovavavaaa.data.SQLQueries.UsersQueries
import com.example.kursovavavaaa.data.entity.Difficulty
import com.example.kursovavavaaa.data.entity.Exercise
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

        // Create the exercise table
        val createExrciseTable = ExerciseQueries().getCreateExerciseTableQuery()
        executeQuery(db, createExrciseTable)

        // Create difficulty table
        val createDifficultyTable = DifficultyQuiries().getCreateDifficultyTableQuery()
        executeQuery(db, createDifficultyTable)

    }

    // Update database actions
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // Delete the users table
        val deleteUserTable = UsersQueries().deleteUsersTable()
        executeQuery(db!!, deleteUserTable)

        // Delete the exercise table
        val deleteExerciseTable = ExerciseQueries().getDeleteExerciseTableQuery()
        executeQuery(db, deleteExerciseTable)

        // Delete the difficulty table
        val deleteDifficultyTable = DifficultyQuiries().getDeleteDifficultyTableQuery()
        executeQuery(db, deleteDifficultyTable)

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

    // Get the exercise list
    fun getExerciseList(): List<Exercise> {
        val query = "SELECT * FROM exercise"
        val db = readableDatabase
        val exercises = mutableListOf<Exercise>()

        val cursor = db.rawQuery(query, null)

        try {
            while (cursor.moveToNext()) {
                val exercise = Exercise(
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    image = cursor.getString(cursor.getColumnIndexOrThrow("image")),
                    type = cursor.getString(cursor.getColumnIndexOrThrow("type")),
                    difficulty = cursor.getString(cursor.getColumnIndexOrThrow("difficulty")),
                    points = cursor.getInt(cursor.getColumnIndexOrThrow("points")),
                    time = cursor.getInt(cursor.getColumnIndexOrThrow("time")),
                    calories = cursor.getInt(cursor.getColumnIndexOrThrow("calories"))
                )
                exercises.add(exercise)
            }
        } finally {
            cursor.close()
        }

        return exercises
    }

    // Create the exercises
    fun createExercises() {
        val exercises = listOf(
            Exercise(
                name = "Push-ups",
                description = "Push-ups are a great exercise for your chest, shoulders, and triceps. They can be done anywhere and require no equipment.",
                image = "pushups",
                type = "Strength",
                difficulty = "Beginner",
                calories = 100,
                points = 10,
                time = 30
            ),
            Exercise(
                name = "Squats",
                description = "Squats help strengthen your legs and glutes. They are essential for lower body strength and stability.",
                image = "squats",
                type = "Strength",
                difficulty = "Beginner",
                points = 12,
                time = 30,
                calories = 100
            ),
            Exercise(
                name = "Plank",
                description = "Plank is a core strength exercise that helps improve stability and endurance.",
                image = "plank",
                type = "Core",
                difficulty = "Intermediate",
                points = 15,
                time = 45,
                calories = 100
            ),
            Exercise(
                name = "Lunges",
                description = "Lunges are great for building lower body strength and improving balance.",
                image = "lunges",
                type = "Strength",
                difficulty = "Intermediate",
                points = 20,
                time = 45,
                calories = 100
            ),
            Exercise(
                name = "Burpees",
                description = "Burpees are a full-body exercise that improves cardio and strength simultaneously.",
                image = "burpees",
                type = "Cardio",
                difficulty = "Advanced",
                points = 25,
                time = 60,
                calories = 100
            )
        )

        val dbWritable = writableDatabase

        try {
            exercises.forEach { exercise ->
                // Check if exercise with the same name exists
                val query = "SELECT * FROM exercise WHERE name = ?"
                val cursor = dbWritable.rawQuery(query, arrayOf(exercise.name))

                if (cursor.moveToFirst()) {
                    cursor.close()
                    return@forEach
                }
                cursor.close()

                val values = ContentValues().apply {
                    put("name", exercise.name)
                    put("description", exercise.description)
                    put("image", exercise.image)
                    put("type", exercise.type)
                    put("difficulty", exercise.difficulty)
                    put("points", exercise.points)
                    put("time", exercise.time)
                    put("calories", exercise.calories)
                }

                dbWritable.insert("exercise", null, values)
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    // Get the difficulty list
    fun getDifficultyList(): List<Difficulty> {
        val query = "SELECT * FROM difficulty"
        val db = readableDatabase
        val difficulties = mutableListOf<Difficulty>()

        val cursor = db.rawQuery(query, null)

        try {
            while (cursor.moveToNext()) {
                val difficulty = Difficulty(
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    repsMult = cursor.getFloat(cursor.getColumnIndexOrThrow("repsMult")),
                    pointsMult = cursor.getFloat(cursor.getColumnIndexOrThrow("pointsMult")),
                    time = cursor.getInt(cursor.getColumnIndexOrThrow("time")),
                    calories = cursor.getInt(cursor.getColumnIndexOrThrow("calories"))
                )
                difficulties.add(difficulty)
            }
        } finally {
            cursor.close()
        }

        return difficulties
    }

    // Create the difficulties
    fun createDifficulties() {
        val difficulties = listOf(
            Difficulty(
                name = "Beginner",
                repsMult = 1.0f,
                pointsMult = 1.0f,
                time = 30,
                calories = 100
            ),
            Difficulty(
                name = "Intermediate",
                repsMult = 1.5f,
                pointsMult = 1.5f,
                time = 45,
                calories = 100
            ),
            Difficulty(
                name = "Advanced",
                repsMult = 2.0f,
                pointsMult = 2.0f,
                time = 60,
                calories = 100
            )
        )

        val dbWritable = writableDatabase

        try {
            difficulties.forEach { difficulty ->
                // Check if difficulty with the same name exists
                val query = "SELECT * FROM difficulty WHERE name = ?"
                val cursor = dbWritable.rawQuery(query, arrayOf(difficulty.name))

                if (cursor.moveToFirst()) {
                    cursor.close()
                    return@forEach
                }
                cursor.close()

                val values = ContentValues().apply {
                    put("name", difficulty.name)
                    put("repsMult", difficulty.repsMult)
                    put("pointsMult", difficulty.pointsMult)
                    put("time", difficulty.time)
                    put("calories", difficulty.calories)
                }

                dbWritable.insert("difficulty", null, values)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error $e.message", Toast.LENGTH_LONG).show()
        }
    }
}