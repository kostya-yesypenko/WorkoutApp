package com.example.kursovavavaaa.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kursovavavaaa.R
import com.example.kursovavavaaa.data.Database
import com.example.kursovavavaaa.databinding.FragmentUserBinding
import java.io.File

class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)

        // Fill user data fields
        fillUserData()

        binding?.editBtn?.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.editProfileFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Fill user data fields
    fun fillUserData() {
        try {
            // Get the user from the database
            val db = Database(requireContext(), null)
            val user = db.getUser()

            // If user is not null, fill the fields
            if (user != null) {
                binding.greetingsMessage.text = "Вітаємо, ${user.name}"
                binding.heightText.text = user.height.toString()
                binding.weightText.text = user.weight.toString()
                binding.ageText.text = user.age.toString()
                binding.genderText.text = user.gender.toString()

                // If user has an image, set it
                if (user.image != "emptyuser") {
                    // Set the image
                    val file = File(user.image)

                    // If the file exists, set the image
                    if (file.exists()) {
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        binding.userImg.setImageBitmap(bitmap)
                    }
                }
            }
        } catch (e: Exception) {
            // Show error message
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }
}