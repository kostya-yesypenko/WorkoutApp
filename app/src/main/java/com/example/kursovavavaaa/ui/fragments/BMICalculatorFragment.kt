package com.example.kursovavavaaa.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.kursovavavaaa.R
import com.example.kursovavavaaa.databinding.FragmentBmiCalculatorBinding

class BMICalculatorFragment : Fragment() {
    private var _binding: FragmentBmiCalculatorBinding? = null
    private val binding get() = _binding!!

    private val weight = 80f // Example weight in kg
    private val height = 1.8f // Example height in meters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmiCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bmi = calculateBMI(weight, height)
        val category = getBMICategory(bmi)
        val color = getCategoryColor(bmi)

        // Update BMIScaleView
        binding.bmiScaleView.setBmiValue(bmi)

        // Update result text and set its color
        binding.textViewBmiInfo.text = "Your BMI: $bmi\nCategory: $category"
        binding.textViewBmiInfo.setTextColor(ContextCompat.getColor(requireContext(), color))
    }

    private fun calculateBMI(weight: Float, height: Float): Float {
        return (weight / (height * height) * 10).toInt() / 10f // Rounded to one decimal
    }

    private fun getBMICategory(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi in 18.5..24.9 -> "Normal weight"
            bmi in 25.0..29.9 -> "Overweight"
            else -> "Obesity"
        }
    }

    private fun getCategoryColor(bmi: Float): Int {
        return when {
            bmi < 18.5 -> R.color.underweightColor // Light blue
            bmi in 18.5..24.9 -> R.color.normalColor // Light green
            bmi in 25.0..29.9 -> R.color.overweightColor // Yellow
            else -> R.color.obesityColor // Red
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
