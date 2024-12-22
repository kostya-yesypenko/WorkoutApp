package com.example.kursovavavaaa.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kursovavavaaa.R
import com.example.kursovavavaaa.databinding.FragmentWaterTrackerBinding

class WaterTrackerFragment : Fragment() {
    private var _binding: FragmentWaterTrackerBinding? = null
    private val binding get() = _binding!!

    private var currentWater = 0
    private var targetWater = 2000 // Default target in milliliters

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("WaterTrackerPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaterTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load saved values
        targetWater = sharedPreferences.getInt("targetConsumption", 2000)
        currentWater = sharedPreferences.getInt("currentConsumption", 0)

        updateProgress()

        // "+250 ml" button logic
        binding.buttonAddWater.setOnClickListener {
            currentWater += 250
            if (currentWater >= targetWater) {
                currentWater = targetWater
                showTargetReachedToast() // Show toast when target is reached
            }
            saveConsumption()
            updateProgress()
        }

        // Reset button logic
        binding.buttonReset.setOnClickListener {
            currentWater = 0
            saveConsumption()
            updateProgress()
        }

        // Set target button logic
        binding.buttonSetTarget.setOnClickListener {
            // Show the input field and Apply button
            binding.editTextNewTarget.visibility = View.VISIBLE
            binding.buttonApplyNewTarget.visibility = View.VISIBLE
            binding.buttonSetTarget.visibility = View.GONE
        }

        // Apply new target button logic
        binding.buttonApplyNewTarget.setOnClickListener {
            val newTarget = binding.editTextNewTarget.text.toString().toIntOrNull()
            if (newTarget != null && newTarget > 0) {
                targetWater = newTarget
                currentWater = 0 // Optionally reset the current water intake
                saveConsumption()
                updateProgress()
            }
            // Hide the input field and Apply button, and show the Set Target button
            binding.editTextNewTarget.visibility = View.GONE
            binding.buttonApplyNewTarget.visibility = View.GONE
            binding.buttonSetTarget.visibility = View.VISIBLE
        }
    }

    private fun updateProgress() {
        binding.textViewWaterProgress.text = "$currentWater / $targetWater ml"
        binding.progressBarWater.progress = (currentWater * 100 / targetWater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showTargetReachedToast() {
        // Show a Toast message when the target is reached
        Toast.makeText(requireContext(), "Congratulations! You've reached your water consumption target!", Toast.LENGTH_LONG).show()
    }

    private fun saveConsumption() {
        with(sharedPreferences.edit()) {
            putInt("targetConsumption", targetWater)
            putInt("currentConsumption", currentWater)
            apply()
        }
    }
}
