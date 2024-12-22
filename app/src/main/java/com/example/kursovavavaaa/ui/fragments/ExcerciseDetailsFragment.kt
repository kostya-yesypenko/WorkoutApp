package com.example.kursovavavaaa.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.kursovavavaaa.databinding.FragmentExcerciseDetailsBinding

class ExcerciseDetailsFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())
    private var progress = 0
    private val duration = 10000 // 10秒 (ミリ秒)
    private var isRunning = false // 進行中かどうかを記録

    private var _binding: FragmentExcerciseDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExcerciseDetailsBinding.inflate(inflater, container, false)

        val progressBar = binding.progressBar
        val timeText = binding.timeText
        timeText.text = calcTime(progress, progressBar.max)

        binding.button.setOnClickListener {
            if (isRunning) {
                stopProgressBar()
            } else {
                startProgressBar(progressBar, timeText)
            }
        }

        return binding.root
    }

    private fun calcTime(progress: Int, maxProgress: Int): String {
        val remainingTime = duration - (progress * duration / maxProgress)
        val minutes = remainingTime / 1000 / 60
        val seconds = (remainingTime / 1000 % 60).toString().padStart(2, '0')
        return "$minutes:$seconds"
    }

    private fun startProgressBar(progressBar: ProgressBar, timeText: TextView) {
        val interval = 100L
        val maxProgress = progressBar.max

        isRunning = true
        binding.button.text = "中止" // ボタンのテキストを中止に変更

        handler.post(object : Runnable {
            override fun run() {
                progress += (maxProgress * interval / duration).toInt()
                timeText.text = calcTime(progress, maxProgress)

                if (progress <= maxProgress) {
                    progressBar.progress = progress
                    handler.postDelayed(this, interval)
                } else {
                    progressBar.progress = maxProgress
                    stopProgressBar() // 完了時に停止
                }
            }
        })
    }

    private fun stopProgressBar() {
        isRunning = false
        binding.button.text = "開始" // ボタンのテキストを開始に変更
        handler.removeCallbacksAndMessages(null) // 全てのタスクを停止
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // メモリリークを防ぐ
        stopProgressBar() // 画面破棄時に停止
    }
}
