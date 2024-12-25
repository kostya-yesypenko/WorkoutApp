package com.example.kursovavavaaa.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.kursovavavaaa.R
import com.example.kursovavavaaa.databinding.FragmentCalorieCalculatorBinding

class CalorieCalculatorFragment : Fragment() {
    private var _binding: FragmentCalorieCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalorieCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCalculateCalories.setOnClickListener {
            calculateCalories()
        }
    }

    private fun calculateCalories() {
        val weight = binding.editTextWeight.text.toString().toFloatOrNull()
        val height = binding.editTextHeight.text.toString().toFloatOrNull()
        val age = binding.editTextAge.text.toString().toIntOrNull()

        if (weight != null && height != null && age != null) {
            val bmr = 10 * weight + 6.25 * height - 5 * age + 5 // Для чоловіків

            val activityMultiplier = when (binding.radioGroupActivityLevel.checkedRadioButtonId) {
                R.id.radioButtonSedentary -> 1.2
                R.id.radioButtonLightlyActive -> 1.375
                R.id.radioButtonModeratelyActive -> 1.55
                R.id.radioButtonVeryActive -> 1.725
                R.id.radioButtonExtraActive -> 1.9
                else -> 1.2 // Default
            }

            val maintenanceCalories = bmr * activityMultiplier
            val weightLossCalories = maintenanceCalories - 500
            val weightGainCalories = maintenanceCalories + 500

            binding.textViewResults.text = """
                Підтримка: ${maintenanceCalories.toInt()} ккал/д.
                Схудення: ${weightLossCalories.toInt()} ккал/д.
                Набір: ${weightGainCalories.toInt()} ккал/д.
            """.trimIndent()

            binding.textViewResults.visibility = View.VISIBLE
        } else {
            binding.textViewResults.text = "Заповніть усі поля."
            binding.textViewResults.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
