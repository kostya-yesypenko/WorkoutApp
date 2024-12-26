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
        values.put("image", user.image)

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
    fun getExerciseByName(name: String): Exercise {
        val query = "SELECT * FROM exercise WHERE name = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(query, arrayOf(name.toString()))

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

    fun getExerciseById(id: Int): Exercise {
        val query = "SELECT * FROM exercise WHERE id = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        cursor.moveToFirst()

        return Exercise(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
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
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
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
                name = "Віджимання",
                description = "Віджимання — чудова вправа для грудей, плечей і трицепсів. Їх можна робити де завгодно і не потребує обладнання.",
                image = "pushups",
                type = "Strength",
                calories = 100,
                reps = 10,
                points = 10,
            ),
            Exercise(
                name = "Присідання",
                description = "Присідання допомагають зміцнити ноги та сідниці. Вони необхідні для міцності та стабільності нижньої частини тіла.",
                image = "squats",
                type = "Strength",
                points = 12,
                reps = 10,
                calories = 100
            ),
            Exercise(
                name = "Планка",
                description = "Планка – це основна силова вправа, яка допомагає підвищити стабільність і витривалість.",
                image = "plank",
                type = "Core",
                points = 15,
                reps = 10,
                calories = 100
            ),
            Exercise(
                name = "Випади",
                description = "Випади чудово підходять для розвитку сили нижньої частини тіла та покращення рівноваги.",
                image = "lunges",
                type = "Strength",
                points = 20,
                reps = 10,
                calories = 100
            ),
            Exercise(
                name = "Підтягування",
                description = "Підтягування - чудова вправа для спини і біцепсів. Їх можна виконувати з підтягуванням.",
                image = "pullups",
                type = "Strength",
                points = 25,
                reps = 10,
                calories = 100
            ),
            Exercise(
                name = "Біг",
                description = "Біг - це чудовий спосіб підтримувати форму та здоров'я. Він покращує фізичну форму та здоров'я.",
                image = "running",
                type = "Cardio",
                points = 30,
                reps = 10,
                calories = 100
            ),
            Exercise(
                name = "Велосипед",
                description = "Велосипед - це чудовий спосіб підтримувати форму та здоров'я. Він покращує фізичну форму та здоров'я.",
                image = "cycling",
                type = "Cardio",
                points = 35,
                reps = 10,
                calories = 100
            ),
            Exercise(
                name = "Стрибки на місці",
                description = "Стрибки на місці допомагають розвивати кардіо витривалість і зміцнювати м'язи ніг.",
                image = "jumping_jacks",
                type = "Cardio",
                points = 20,
                reps = 30,
                calories = 120
            ),
            Exercise(
                name = "Скручування",
                description = "Скручування - вправа для преса, яка покращує гнучкість і зміцнює м'язи черевного преса.",
                image = "crunches",
                type = "Core",
                points = 15,
                reps = 30,
                calories = 80
            ),
            Exercise(
                name = "Махи ногами",
                description = "Махи ногами сприяють розвитку сили і витривалості в ногах, а також покращують баланс.",
                image = "leg_swings",
                type = "Strength",
                points = 15,
                reps = 20,
                calories = 90
            ),
            Exercise(
                name = "Підйом ніг",
                description = "Підйом ніг допомагає тренувати м'язи живота і покращувати стабільність тіла.",
                image = "leg_raises",
                type = "Core",
                points = 20,
                reps = 20,
                calories = 100
            ),
            Exercise(
                name = "Сідання на стілець",
                description = "Сідання на стілець - проста вправа для зміцнення ніг і сідниць, що виконується без обладнання.",
                image = "chair_squats",
                type = "Strength",
                points = 20,
                reps = 15,
                calories = 110
            ),
            Exercise(
                name = "Підйом на носки",
                description = "Підйом на носки зміцнює м'язи литок і покращує координацію.",
                image = "calf_raises",
                type = "Strength",
                points = 10,
                reps = 25,
                calories = 60
            ),
            Exercise(
                name = "Скакалка",
                description = "Стрибки через скакалку покращують кардіо витривалість і зміцнюють ноги.",
                image = "jump_rope",
                type = "Cardio",
                points = 30,
                reps = 30,
                calories = 150
            ),
            Exercise(
                name = "Тяга тіла до колін",
                description = "Тяга тіла до колін допомагає зміцнити спину і покращити гнучкість.",
                image = "body_pull_to_knees",
                type = "Strength",
                points = 20,
                reps = 20,
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
                name = "Початковий",
                repsMult = 1.0f,
                pointsMult = 1.0f,
                time = 30,
                caloriesMult = 1f
            ),
            Difficulty(
                name = "Середній",
                repsMult = 1.5f,
                pointsMult = 1.5f,
                time = 45,
                caloriesMult = 1.5f
            ),
            Difficulty(
                name = "Складний",
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