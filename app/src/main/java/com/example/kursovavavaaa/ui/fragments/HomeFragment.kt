package com.example.kursovavavaaa.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kursovavavaaa.R
import com.example.kursovavavaaa.databinding.FragmentHomeBinding
import kotlin.math.roundToInt

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var bmiScaleView: BMIScaleView

    // Hardcoded weight and height for testing
    private val weight = 90f  // kg
    private val height = 1.9f  // meters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the BMIScaleView
        bmiScaleView = binding.root.findViewById(R.id.bmiScaleView)

        // Calculate BMI
        val bmi = calculateBMI(weight, height)

        // Set the BMI value on the scale view
        bmiScaleView.setBmiValue(bmi)

        // Display the BMI category under the scale
        val category = getBMICategory(bmi)
        displayBMIInfo(bmi, category)
    }

    // Calculate BMI
    private fun calculateBMI(weight: Float, height: Float): Float {
        return (weight / (height * height)).toDouble().roundToOneDecimal().toFloat()
    }

    // Extension function to round Double to one decimal place
    private fun Double.roundToOneDecimal(): Double {
        return String.format("%.1f", this).toDouble()
    }

    // Determine BMI category
    private fun getBMICategory(bmi: Float): String {
        return when {
            bmi < 18.5f -> "Underweight"
            bmi in 18.5f..24.9f -> "Normal weight"
            bmi in 25f..29.9f -> "Overweight"
            else -> "Obesity"
        }
    }

    // Display BMI information below the scale
    private fun displayBMIInfo(bmi: Float, category: String) {
        val bmiText = "Your BMI: $bmi"
        val categoryText = "Category: $category"

        binding.textViewHome.text = "$bmiText\n$categoryText"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
