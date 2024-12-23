package com.example.kursovavavaaa.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.example.kursovavavaaa.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var _binding: FragmentSettingsBinding
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private val THEME_PREF_KEY = "theme_preference"
    private val NOTIFICATIONS_PREF_KEY = "notifications_preference"

    // Show the theme choice dialog
    private fun showChoiceDialog() {
        val choices = arrayOf("Світлий", "Темний")
        val selectedItem = if (isNightMode()) 1 else 0

        AlertDialog.Builder(requireContext())
            .setTitle("Обіреть вигляд")
            .setSingleChoiceItems(choices, selectedItem) { _, which ->
                // Save the selected theme
                saveThemePreference(which)
                // Set the selected theme
                if (which == 1)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            .setPositiveButton("Готово") { _, _ -> }
            .setNegativeButton("Назад", null)
            .show()
    }

    // Save the theme preference to SharedPreferences
    private fun saveThemePreference(themeMode: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(THEME_PREF_KEY, themeMode)
        editor.apply()
    }

    // Check the saved theme preference from SharedPreferences
    private fun isNightMode(): Boolean {
        return sharedPreferences.getInt(THEME_PREF_KEY, 0) == 1
    }

    // Save notification setting
    private fun saveNotificationPreference(isChecked: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(NOTIFICATIONS_PREF_KEY, isChecked)
        editor.apply()
    }

    // Check if notifications are enabled
    private fun isNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean(NOTIFICATIONS_PREF_KEY, true) // Default to true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        // Initially apply the theme when the view is created
        if (isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.themeState.text = "Темний"
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.themeState.text = "Світлий"
        }

        // Set the button to open the theme choice dialog
        binding.themeState.setOnClickListener {
            showChoiceDialog()
        }

        // Restore notification preference and update the checkbox
        binding.checkBox.isChecked = isNotificationsEnabled()

        // Save notification setting when checkbox is toggled
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            saveNotificationPreference(isChecked)
        }

        return binding.root
    }
}
