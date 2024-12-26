package com.example.kursovavavaaa.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.transition.Visibility
import com.example.kursovavavaaa.R
import com.example.kursovavavaaa.data.Database
import com.example.kursovavavaaa.data.entity.Difficulty
import com.example.kursovavavaaa.data.entity.Exercise
import com.example.kursovavavaaa.databinding.FragmentExcerciseDetailsBinding
import java.io.File

class ExcerciseDetailsFragment : Fragment() {
    private val handler = Handler(Looper.getMainLooper())
    private var progress = 0.0
    private var isRunning = false

    // Duration of exercise in milliseconds
    private var duration = 7000

    private var _binding: FragmentExcerciseDetailsBinding? = null

    private val binding get() = _binding!!

    // Exercise data
    private var exercise: Exercise? = null
    // Difficulty data
    private var difficultys: List<Difficulty>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExcerciseDetailsBinding.inflate(inflater, container, false)

        // Get exercise name from arguments
        val exerciseName = arguments?.getString("exerciseName")
        // Get exercise data from database with exercise name and difficultys
        getExerciseAndDifficultysData(exerciseName!!)


        val imageResourceId = resources.getIdentifier(exercise?.image, "drawable", context?.packageName)
        if (imageResourceId != 0) {
            binding.exerciseImage.setImageResource(imageResourceId)
        } else {
            binding.exerciseImage.setImageResource(R.drawable.kaczok)
        }



        val progressBar = binding.progressBar
        val timeText = binding.button
        timeText.text = calcTime(duration, progress, progressBar.max)

        // Get difficulty spinner
        val difficultySpinner: Spinner = binding.difficultyEdit

        // Difficulty options
        val difficultyOptions = listOf(
            difficultys?.get(0)?.name.toString(),
            difficultys?.get(1)?.name.toString(),
            difficultys?.get(2)?.name.toString()
        )

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, difficultyOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        difficultySpinner.adapter = adapter

        difficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                //Дійсні значення беруться з бази даних
                when (selectedItem) {
                    "Початковий" -> duration = difficultys?.get(0)?.time!! * 1000
                    "Середній" -> duration = difficultys?.get(1)?.time!! * 1000
                    "Складний" -> duration = difficultys?.get(2)?.time!! * 1000
                }

                timeText.text = calcTime(duration, progress, progressBar.max)

                Log.println(Log.DEBUG, "AAA", "Selected item: $selectedItem")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        binding.button.setOnClickListener {
            if (isRunning) {
                stopProgressBar()
            } else {
                startProgressBar(duration, progressBar, timeText)
            }
        }



        return binding.root
    }

    // Get exercise data from database
    private fun getExerciseAndDifficultysData(exerciseName: String) {
        // Get exercise data from database
        val db = Database(requireContext(), null)
        exercise = db.getExerciseByName(exerciseName)

        difficultys = db.getDifficultyList()
    }


    private fun calcTime(duration: Int, progress: Double, maxProgress: Int): String {
        val remainingTime = duration - (progress * duration / maxProgress)
        val minutes = (remainingTime / 1000 / 60).toInt()
        val seconds = ((remainingTime / 1000 % 60).toInt()).toString().padStart(2, '0')
        if (minutes == 0 && seconds == "00") {
            "Закінчено"
        }
        return "$minutes:$seconds"
    }

    private fun startProgressBar(duration: Int, progressBar: ProgressBar, timeText: TextView) {
        val interval = 100L
        val maxProgress = progressBar.max

        isRunning = true
        binding.button.text = "Зупинити"


        handler.post(object : Runnable {
            override fun run() {
                if (progress < maxProgress) {
                    Log.d(
                        "ProgressBar",
                        "Progress: $progress, Time Left: ${
                            calcTime(
                                duration,
                                progress,
                                progressBar.max
                            )
                        }, riznycia ${(maxProgress.toFloat() * interval / duration).toInt()}"
                    )
                    val change =(maxProgress.toFloat() * interval / duration)
                    progress += change
                    timeText.text = calcTime(duration, progress, maxProgress)
                    progressBar.progress = progress.toInt()

                    handler.postDelayed(this, interval)
                } else {
                    progressBar.progress = maxProgress
                    timeText.text = "Закінчено"
                }
            }
        })
    }

    private fun stopProgressBar() {
        binding.button.text = "Продовжити"
        isRunning = false
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        isRunning = false
        handler.removeCallbacksAndMessages(null)
    }
}
