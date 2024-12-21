package com.example.kursovavavaaa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kursovavavaaa.databinding.ActivityMainBinding
import com.example.kursovavavaaa.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var _binding: FragmentSettingsBinding
    private val binding get() = _binding!!

    private fun showChoiceDialog() {
        val choices = arrayOf("Світлий", "Темний", "Системний") // 選択肢
        var selectedItem = -1 // 選択されたアイテムのインデックス

        AlertDialog.Builder(requireContext())
            .setTitle("Обіреть вигляд")
            .setSingleChoiceItems(choices, selectedItem) { _, which ->
                selectedItem = which // 選択されたインデックスを更新
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

        binding.button2.setOnClickListener {
            showChoiceDialog()
        }
        return binding.root
    }

}