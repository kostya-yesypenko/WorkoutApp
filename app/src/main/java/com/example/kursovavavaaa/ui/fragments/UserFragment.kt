package com.example.kursovavavaaa.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kursovavavaaa.R
import com.example.kursovavavaaa.databinding.FragmentUserBinding

class UserFragment : Fragment() {
    private lateinit var _binding: FragmentUserBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserBinding.inflate(inflater, container, false)

        binding.editBtn.setOnClickListener {

            val navController = findNavController()
            navController.navigate(R.id.editProfileFragment)
        }
        return binding.root
    }

}