package com.example.kursovavavaaa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kursovavavaaa.data.Database
import com.example.kursovavavaaa.data.entity.Exercise
import com.example.kursovavavaaa.databinding.ExerciseItemBinding
//import com.example.kursovavavaaa.ui.fragments.a


class ExerciseItemAdapter(
    private val navController: NavController,
    private val exerciseList: List<Exercise>
) : RecyclerView.Adapter<ExerciseItemAdapter.ExerciseItemHolder>() {

    inner class ExerciseItemHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ExerciseItemBinding.bind(item)

        fun bind(exerciseItem: Exercise) {
            binding.exerciseTitle.text = exerciseItem.name
            binding.exerciseDescription.text = exerciseItem.description

            val imageResourceId = itemView.context.resources.getIdentifier(
                exerciseItem.image,
                "drawable",
                itemView.context.packageName
            )

            if (imageResourceId != 0) {
                binding.exerciseImage.setImageResource(imageResourceId)
            } else {

                binding.exerciseImage.setImageResource(R.drawable.kaczok)
            }

            val bundle = Bundle().apply {
                putString("exerciseName", exerciseItem.name.toString())
            }

            binding.button.setOnClickListener {
                navController.navigate(R.id.action_exercisesFragment_to_excerciseDetailsFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseItemHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseItemHolder, position: Int) {
        holder.bind(exerciseList[position])
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }
}