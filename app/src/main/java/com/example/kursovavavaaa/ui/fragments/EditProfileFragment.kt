package com.example.kursovavavaaa.ui.fragments

import android.R
import android.os.Bundle
import android.util.Log
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

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        // Load data from database
        //loadDataFromDatabase()

        // Set up the Spinner for gender selection
        val genderSpinner: Spinner = binding.genderEdit
        val genderOptions = listOf("Other", "Male", "Female")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        // Access the button using binding
        binding.saveButton.setOnClickListener {
            // Call the function to insert data to database
            //insertDataToDatabase()
        }

        // Return the view
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Function to insert data to database
    private fun insertDataToDatabase() {
        println("Okkk")
        // Get the values from the edit text fields
        val height = _binding?.heightEdit?.text.toString().toFloat() ?: 0.0f
        val weight = _binding?.weightEdit?.text.toString().toFloat() ?: 0.0f
        val age = _binding?.ageEdit?.text.toString().toInt() ?: 0
        val name = _binding?.nameEdit?.text.toString() ?: ""

        val gender = _binding?.genderEdit?.selectedItem.toString() ?: "Other"

        println("Ok")

        // Insert data to database
        val user = User (
            id = 1,
            name = name,
            height = height,
            weight = weight,
            gender = gender,
            age = age,
        )

        // Insert data to database
        try {
            // Create the database
            //val db = Database(requireContext(), null)
            // Update the user
            //db?.updateUser(user)
            // Show the toast message
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("EditProfileFragment", "Ошибка добавления пользователя", e)
        }
    }

//    private fun loadDataFromDatabase() {
//        val db = Database(requireContext(), null)
//        val user = db.getUser()
//        user?.let {
//            _binding!!.nameEdit.setText(it.name)
//            _binding!!.heightEdit.setText(it.height.toString())
//            _binding!!.weightEdit.setText(it.weight.toString())
//            _binding!!.ageEdit.setText(it.age.toString())
//            val genderIndex = listOf("Other", "Male", "Female").indexOf(it.gender)
//            _binding!!.genderEdit.setSelection(if (genderIndex >= 0) genderIndex else 0)
//        }
//    }
}
