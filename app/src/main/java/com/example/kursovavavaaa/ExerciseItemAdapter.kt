package com.example.kursovavavaaa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kursovavavaaa.databinding.ExerciseItemBinding



class ExerciseItemAdapter : RecyclerView.Adapter<ExerciseItemAdapter.ExerciseItemHolder>() {
    val ExerciseList = a()

    class ExerciseItemHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ExerciseItemBinding.bind(item)
        fun bind(exerciseItem: ExerciseItem) {
            binding.exerciseTitle.text = exerciseItem.title
            binding.exerciseDescription.text = exerciseItem.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseItemHolder(view)
    }

    override fun getItemCount(): Int {
        return ExerciseList.size
    }

    override fun onBindViewHolder(holder: ExerciseItemHolder, position: Int) {
        holder.bind(ExerciseList[position])
    }

}
