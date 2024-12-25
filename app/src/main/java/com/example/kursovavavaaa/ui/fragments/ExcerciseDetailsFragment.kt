package com.example.kursovavavaaa.ui.fragments

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
import androidx.transition.Visibility
import com.example.kursovavavaaa.databinding.FragmentExcerciseDetailsBinding

class ExcerciseDetailsFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())
    private var progress = 0.0
    private var isRunning = false

    private var _binding: FragmentExcerciseDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var duration = 7000//Має братись з бази

        _binding = FragmentExcerciseDetailsBinding.inflate(inflater, container, false)

        val progressBar = binding.progressBar
        val timeText = binding.timeText
        timeText.text = calcTime(duration, progress, progressBar.max)


        // Вибір складності вправи
        val difficultySpinner: Spinner = binding.difficultyEdit
        //Тут мають значення братись з бази
        val difficultyOptions = listOf("Низька", "Середня", "Висока")

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
                    "Низька" -> duration = 7000
                    "Середня" -> duration = 10000
                    "Висока" -> duration = 12000
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

    private fun calcTime(duration: Int, progress: Double, maxProgress: Int): String {
        val remainingTime = duration - (progress * duration / maxProgress)
        val minutes = (remainingTime / 1000 / 60).toInt()
        val seconds = ((remainingTime / 1000 % 60).toInt()).toString().padStart(2, '0')
        return "$minutes:$seconds"
    }

    private fun startProgressBar(duration: Int, progressBar: ProgressBar, timeText: TextView) {
        val interval = 100L
        val maxProgress = progressBar.max

        isRunning = true
        binding.button.text = "Зупинити"

        binding.difficultyEditLayout.visibility = View.INVISIBLE


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
                    finishProgressBar()
                }
            }
        })
    }


    private fun finishProgressBar() {
        binding.button.visibility = View.INVISIBLE
        isRunning = false
        handler.removeCallbacksAndMessages(null)
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
