package com.example.kursovavavaaa.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.kursovavavaaa.data.SQLQueries.BmiQueries
import com.example.kursovavavaaa.data.SQLQueries.DifficultyQueries
import com.example.kursovavavaaa.data.SQLQueries.ExerciseQueries
import com.example.kursovavavaaa.data.SQLQueries.UsersQueries
import com.example.kursovavavaaa.data.entity.BMI
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
        val createDifficultyTable = DifficultyQueries().getCreateDifficultyTableQuery()
        executeQuery(db, createDifficultyTable)

        // Create the BMI table
        val createBmiTable = BmiQueries().getCreateBmiTableQuery()
        executeQuery(db, createBmiTable)

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
        val deleteDifficultyTable = DifficultyQueries().getDeleteDifficultyTableQuery()
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
        values.put("image", "emptyuser")

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
                    age = cursor.getInt(cursor.getColumnIndexOrThrow("age")),
                    image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
                )
            }
        } finally {
            // Close cursor
            cursor.close()
        }

        // Return the user (User)
        return user
    }

    // Get exercise by id
    fun getExerciseById(id: Int): Exercise {
        val query = "SELECT * FROM exercise WHERE id = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        cursor.moveToFirst()

        return Exercise(
            name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
            description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
            image = cursor.getString(cursor.getColumnIndexOrThrow("image")),
            type = cursor.getString(cursor.getColumnIndexOrThrow("type")),
            points = cursor.getInt(cursor.getColumnIndexOrThrow("points")),
            reps = cursor.getInt(cursor.getColumnIndexOrThrow("reps")),
            calories = cursor.getInt(cursor.getColumnIndexOrThrow("calories"))
        )
    }

    // Get the exercise list
    fun getExerciseList(): List<Exercise> {
        // Define the query
        val query = "SELECT * FROM exercise"
        // Get the database
        val db = readableDatabase
        // Initialize the list
        val exercises = mutableListOf<Exercise>()

        // Get the cursor and handle it
        val cursor = db.rawQuery(query, null)

        // Try to get the data
        try {
            // Loop through the cursor
            while (cursor.moveToNext()) {
                // Get the exercise
                val exercise = Exercise(
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    image = cursor.getString(cursor.getColumnIndexOrThrow("image")),
                    type = cursor.getString(cursor.getColumnIndexOrThrow("type")),
                    points = cursor.getInt(cursor.getColumnIndexOrThrow("points")),
                    reps = cursor.getInt(cursor.getColumnIndexOrThrow("reps")),
                    calories = cursor.getInt(cursor.getColumnIndexOrThrow("calories"))
                )
                // Add the exercise to the list
                exercises.add(exercise)
            }
        } finally {
            // Close the cursor
            cursor.close()
        }

        // Return the list of exercises
        return exercises
    }

    // Create the exercises
    fun createExercises() {
        // Define the exercises
        val exercises = listOf(
            Exercise(
                name = "Push-ups",
                description = "Push-ups are a great exercise for your chest, shoulders, and triceps. They can be done anywhere and require no equipment.",
                image = "pushups",
                type = "Strength",
                calories = 100,
                reps = 10,
                points = 10,
            ),
            Exercise(
                name = "Squats",
                description = "Squats help strengthen your legs and glutes. They are essential for lower body strength and stability.",
                image = "squats",
                type = "Strength",
                points = 12,
                reps = 10,
                calories = 100
            ),
            Exercise(
                name = "Plank",
                description = "Plank is a core strength exercise that helps improve stability and endurance.",
                image = "plank",
                type = "Core",
                points = 15,
                reps = 10,
                calories = 100
            ),
            Exercise(
                name = "Lunges",
                description = "Lunges are great for building lower body strength and improving balance.",
                image = "lunges",
                type = "Strength",
                points = 20,
                reps = 10,
                calories = 100
            ),
            Exercise(
                name = "Pull-ups",
                description = "Pull-ups are a great exercise for your back and biceps. They can be done with a pull-up bar.",
                image = "pullups",
                type = "Strength",
                points = 25,
                reps = 10,
                calories = 100
            )
        )

        // Get the database
        val dbWritable = writableDatabase

        // Try to create the exercises
        try {
            // Loop through the exercises
            exercises.forEach { exercise ->
                // Check if exercise with the same name exists
                val query = "SELECT * FROM exercise WHERE name = ?"
                val cursor = dbWritable.rawQuery(query, arrayOf(exercise.name))

                // If exercise exists, return
                if (cursor.moveToFirst()) {
                    cursor.close()
                    return@forEach
                }
                // Close the cursor
                cursor.close()

                // Define the values
                val values = ContentValues().apply {
                    put("name", exercise.name)
                    put("description", exercise.description)
                    put("image", exercise.image)
                    put("type", exercise.type)
                    put("points", exercise.points)
                    put("calories", exercise.calories)
                    put("reps", exercise.reps)
                }

                // Insert the values
                dbWritable.insert("exercise", null, values)
            }
        } catch (e: Exception) {
            // Show error message
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    // Get the difficulty list
    fun getDifficultyList(): List<Difficulty> {
        // Define the query
        val query = "SELECT * FROM difficulty"
        // Get the database
        val db = readableDatabase
        // Initialize the list
        val difficulties = mutableListOf<Difficulty>()

        // Get the cursor and handle it
        val cursor = db.rawQuery(query, null)

        // Try to get the data
        try {
            // Loop through the cursor
            while (cursor.moveToNext()) {
                // Get the difficulty
                val difficulty = Difficulty(
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    time = cursor.getInt(cursor.getColumnIndexOrThrow("time")),
                    repsMult = cursor.getFloat(cursor.getColumnIndexOrThrow("repsMult")),
                    pointsMult = cursor.getFloat(cursor.getColumnIndexOrThrow("pointsMult")),
                    caloriesMult = cursor.getFloat(cursor.getColumnIndexOrThrow("caloriesMult"))
                )
                // Add the difficulty to the list
                difficulties.add(difficulty)
            }
        } finally {
            // Close the cursor
            cursor.close()
        }

        // Return the list of difficulties
        return difficulties
    }

    // Create the difficulties
    fun createDifficulties() {
        // Define the difficulties
        val difficulties = listOf(
            Difficulty(
                name = "Beginner",
                repsMult = 1.0f,
                pointsMult = 1.0f,
                time = 30,
                caloriesMult = 1f
            ),
            Difficulty(
                name = "Intermediate",
                repsMult = 1.5f,
                pointsMult = 1.5f,
                time = 45,
                caloriesMult = 1.5f
            ),
            Difficulty(
                name = "Advanced",
                repsMult = 2.0f,
                pointsMult = 2.0f,
                time = 60,
                caloriesMult = 2.0f
            )
        )

        // Get the database
        val dbWritable = writableDatabase


        // Try to create the difficulties
        try {
            // Loop through the difficulties
            difficulties.forEach { difficulty ->
                // Check if difficulty with the same name exists
                val query = "SELECT * FROM difficulty WHERE name = ?"
                // Get the cursor
                val cursor = dbWritable.rawQuery(query, arrayOf(difficulty.name))

                // If difficulty exists, return
                if (cursor.moveToFirst()) {
                    // Close the cursor
                    cursor.close()
                    return@forEach
                }
                // Close the cursor
                cursor.close()

                // Define the values
                val values = ContentValues().apply {
                    put("name", difficulty.name)
                    put("repsMult", difficulty.repsMult)
                    put("pointsMult", difficulty.pointsMult)
                    put("time", difficulty.time)
                    put("caloriesMult", difficulty.caloriesMult)
                }
                // Insert the values
                dbWritable.insert("difficulty", null, values)
            }
        } catch (e: Exception) {
            // Show error message
            Toast.makeText(context, "Error $e.message", Toast.LENGTH_LONG).show()
        }
    }

    // Create the BMI
    fun createBmi(value: Float, date: String) {
        // Define the values
        val values = ContentValues().apply {
            put("value", value)
            put("date", date)
        }

        // Get the database
        val db = writableDatabase
        // Insert the values
        db.insert("bmi", null, values)
    }

    // Get the BMI list
    fun getBmiList(): List<BMI> {
        // Define the query
        val query = "SELECT * FROM bmi"
        // Get the database
        val db = readableDatabase
        // Initialize the list
        val bmiList = mutableListOf<BMI>()

        // Get the cursor and handle it
        val cursor = db.rawQuery(query, null)

        // Try to get the data
        try {
            // Loop through the cursor
            while (cursor.moveToNext()) {
                // Get the BMI
                val bmi = BMI(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    value = cursor.getFloat(cursor.getColumnIndexOrThrow("value")),
                    date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                )
                // Add the BMI to the list
                bmiList.add(bmi)
            }
        } finally {
            // Close the cursor
            cursor.close()
        }

        // Return the list of BMI
        return bmiList
    }
}