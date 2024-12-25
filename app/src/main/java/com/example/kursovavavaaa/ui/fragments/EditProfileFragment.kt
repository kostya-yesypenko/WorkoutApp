package com.example.kursovavavaaa.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import com.example.kursovavavaaa.R
import com.example.kursovavavaaa.data.Database
import com.example.kursovavavaaa.data.entity.User
import com.example.kursovavavaaa.databinding.FragmentEditProfileBinding
import java.io.File

class EditProfileFragment : Fragment() {
    // Binding object
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    // Image launcher
    private lateinit var imageLauncher: ActivityResultLauncher<Intent>

    // Create the image launcher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Register the image launcher
        imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                if (imageUri != null) {
                    binding.userImg.setImageURI(imageUri)
                } else {
                    Toast.makeText(requireContext(), "Cant take image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Image selection canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Create the view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        // Set up the image picker button
        binding.pickImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imageLauncher.launch(intent)
        }


        // Set up the Spinner for gender selection
        val genderSpinner: Spinner = binding.genderEdit
        val genderOptions = listOf("Не вказано", "Чоловік", "Жінка")

        // Create an adapter for the spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        // Install the input current fields
        setInputFields()

        // Access the save button using binding
        binding.saveButton.setOnClickListener {
            // Call the function to update data in the database
            insertDataToDatabase()
        }

        // Access the reset button using binding
        binding.resetBtn.setOnClickListener {
            // Call the function to update data in the database
            resetUserData()
        }


        // Return the root view
        return binding.root
    }


    // Destroy the binding when the fragment is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Function to insert data to database
    private fun insertDataToDatabase() {
        try {
            // Get the input fields data
            val height = _binding?.heightEdit?.text.toString().toFloatOrNull() ?: 0.0f
            val weight = _binding?.weightEdit?.text.toString().toFloatOrNull() ?: 0.0f
            val age = _binding?.ageEdit?.text.toString().toIntOrNull() ?: 0
            val name = _binding?.nameEdit?.text.toString() ?: ""
            val gender = _binding?.genderEdit?.selectedItem.toString()

            // Get the image URI
            val imageUri = (binding.userImg.drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap?.let { bitmap ->
                saveBitmapToInternalStorage(bitmap)
            } ?: "emptyuser"

            // Create a UserDbEntity object
            val user = User(
                id =  1,
                name = name,
                height = height,
                weight = weight,
                gender = gender,
                age = age,
                image = imageUri
            )

            // Update the user data in the database
            // Get the database
            val db = Database(requireContext(), null)
            db.updateUser(user)

            Toast.makeText(requireContext(), "Данні збирежені", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // Show a toast message if an error occurs
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    // Set input fields data
    private fun setInputFields() {
        try {
            // Get user from
            val db = Database(requireContext(), null)
            val user = db.getUser()

            // Check if user is not null
            if (user != null) {
                // Fill input fields with user data
                binding.nameEdit.setText(user.name)
                binding.heightEdit.setText(user.height.toString())
                binding.weightEdit.setText(user.weight.toString())
                binding.ageEdit.setText(user.age.toString())

                // Check if the user has an image
                if (user.image != "emptyuser") {
                    val file = File(user.image)

                    // Check if the file exists
                    if (file.exists()) {
                        // Set the image to the ImageView
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        binding.userImg.setImageBitmap(bitmap)
                    }
                }

                // Set up the Spinner
                val genderOptions = listOf("Не вказано", "Чоловік", "Жінка")
                val defaultGender = user.gender.trim()
                val defaultIndex = genderOptions.indexOf(defaultGender)

                if (defaultIndex >= 0) {
                    // Set user gender
                    binding.genderEdit.setSelection(defaultIndex)
                } else {
                    // Set default
                    binding.genderEdit.setSelection(0)
                }

            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun resetUserData() {
        try {
            // Create a UserDbEntity object
            val user = User(
                id =  1,
                name = "Користувач",
                height = 0.0f,
                weight = 0.0f,
                gender = "Не вказано",
                age = 0,
                image = "emptyuser"
            )

            // Update the user data in the database
            // Get the database
            val db = Database(requireContext(), null)
            db.updateUser(user)

            // Show a toast message if the data is reset
            Toast.makeText(requireContext(), "Данні скинуті", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // Show a toast message if an error occurs
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun saveBitmapToInternalStorage(bitmap: Bitmap): String {
        return try {
            // Genreate a file name
            val fileName = "profile_image_${System.currentTimeMillis()}.jpg"

            // Cerete a file in app internal storage
            val file = File(requireContext().filesDir, fileName)

            // Save the bitmap to the file
            val outputStream = file.outputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()

            // Return the file path
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            "emptyuser" // Возврат "emptyuser" в случае ошибки
        }
    }

}
