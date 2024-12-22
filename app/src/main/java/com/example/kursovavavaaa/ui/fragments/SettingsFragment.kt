package com.example.kursovavavaaa.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.example.kursovavavaaa.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var _binding: FragmentSettingsBinding
    private val binding get() = _binding!!

    private fun showChoiceDialog() {
        val choices = arrayOf("Світлий", "Темний")
        var selectedItem = -1

        AlertDialog.Builder(requireContext())
            .setTitle("Обіреть вигляд")
            .setSingleChoiceItems(choices, selectedItem) { _, which ->
                selectedItem = which
                if (selectedItem == 1)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
            .setPositiveButton("Готово") { _, _ ->
            }
            .setNegativeButton("Назад", null)
            .show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.themeState.setOnClickListener {
            showChoiceDialog()
        }
        if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES)
            binding.themeState.text = "Світлий"
        else
            binding.themeState.text = "Темний"

        return binding.root
    }

}