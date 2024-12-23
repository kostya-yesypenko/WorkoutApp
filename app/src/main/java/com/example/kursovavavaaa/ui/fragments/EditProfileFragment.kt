package com.example.kursovavavaaa.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kursovavavaaa.data.Database
import com.example.kursovavavaaa.data.entity.User
import com.example.kursovavavaaa.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    // Binding object
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    // Create the view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        // Set up the Spinner for gender selection
        val genderSpinner: Spinner = binding.genderEdit
        val genderOptions = listOf("Не вказано", "Чоловік", "Жінка")

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

            // Create a UserDbEntity object
            val user = User(
                id =  1,
                name = name,
                height = height,
                weight = weight,
                gender = gender,
                age = age,
            )

            // Update the user data in the database
            // Get the database
            val db = Database(requireContext(), null)
            db.updateUser(user)

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
            // Get the input fields data
            val height = _binding?.heightEdit?.text.toString().toFloatOrNull() ?: 0.0f
            val weight = _binding?.weightEdit?.text.toString().toFloatOrNull() ?: 0.0f
            val age = _binding?.ageEdit?.text.toString().toIntOrNull() ?: 0
            val name = _binding?.nameEdit?.text.toString() ?: ""
            val gender = _binding?.genderEdit?.selectedItem.toString()

            // Create a UserDbEntity object
            val user = User(
                id =  1,
                name = "Користувач",
                height = 0.0f,
                weight = 0.0f,
                gender = "Не вказано",
                age = 0,
            )

            // Update the user data in the database
            // Get the database
            val db = Database(requireContext(), null)
            db.updateUser(user)

        } catch (e: Exception) {
            // Show a toast message if an error occurs
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

}
