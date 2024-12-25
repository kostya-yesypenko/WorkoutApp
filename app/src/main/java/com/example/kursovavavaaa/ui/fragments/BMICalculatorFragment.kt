
package com.example.kursovavavaaa.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.kursovavavaaa.R
import com.example.kursovavavaaa.data.Database
import com.example.kursovavavaaa.databinding.FragmentBmiCalculatorBinding

class BMICalculatorFragment : Fragment() {
    private var _binding: FragmentBmiCalculatorBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmiCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the database
        val database = Database(requireContext(), null)

        // Fetch user data from the database
        val user = database.getUser()  // Assuming getUser() returns a user object with weight and height
        if (user == null) {
            binding.textViewBmiInfo.text = "Не знайдено даних. Будь-ласка заповніть свій запис."
            return
        }

        // Fetch weight and height from user data
        val weight = user.weight
        val height = user.height/100

        // Check if the weight and height are valid (greater than 0)
        if (weight > 0 && height > 0) {
            // Calculate BMI
            val bmi = calculateBMI(weight, height)
            val category = getBMICategory(bmi)
            val color = getCategoryColor(bmi)

            // Update BMIScaleView
            binding.bmiScaleView.setBmiValue(bmi)

            // Update result text and set its color
            binding.textViewBmiInfo.text = "Ваш показник: $bmi\nКатегорія: $category"
            binding.textViewBmiInfo.setTextColor(ContextCompat.getColor(requireContext(), color))
        } else {
            binding.textViewBmiInfo.text = "Будь-ласка вкажіть ваші зріст і вагу у обліковому записі."
        }
    }


    private fun calculateBMI(weight: Float, height: Float): Float {
        return (weight / (height * height) * 10).toInt() / 10f // Rounded to one decimal
    }

    private fun getBMICategory(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Недостача"
            bmi in 18.5..24.9 -> "Нормальна вага"
            bmi in 25.0..29.9 -> "Зайва вага"
            else -> "Ожиріння"
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