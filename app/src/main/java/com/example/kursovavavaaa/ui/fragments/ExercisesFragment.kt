package com.example.kursovavavaaa.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kursovavavaaa.ExerciseItemAdapter
import com.example.kursovavavaaa.data.Database
import com.example.kursovavavaaa.data.entity.Exercise
import com.example.kursovavavaaa.databinding.FragmentExercisesBinding

//fun a():ArrayList<ExerciseItem> {
//    val q = ArrayList<ExerciseItem>()
//    q.add(ExerciseItem("11", "aaaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaa aaaaaaaaaaa ", 1))
//    q.add(ExerciseItem("22", "bbbbbbbbbbbbbbb", 2))
//    q.add(ExerciseItem("33", "cccccccccccccccccccccccccccccccccccccccc", 3))
//    q.add(ExerciseItem("44", "ddddddddddddddddddddddddddddddddddddddddddddd", 4))
//    return q
//}

class ExercisesFragment : Fragment() {

    private var _binding: FragmentExercisesBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        val database = Database(requireContext(), null)
        val exerciseList = database.getExerciseList()

        val recyclerView = binding.exercisesList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = ExerciseItemAdapter(navController, exerciseList)
        binding.exercisesList.layoutManager = LinearLayoutManager(requireContext())
        binding.exercisesList.adapter = adapter

        binding.apply {
            binding.exercisesList.layoutManager = LinearLayoutManager(requireContext())
            exercisesList.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}