package com.rungo.runwithzippy.presentation.features.training

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.data.model.Training
import com.rungo.runwithzippy.databinding.ItemTrainingsBinding
import com.rungo.runwithzippy.utils.EnumTrainingName

class TrainingAdapter : RecyclerView.Adapter<TrainingAdapter.ViewHolder>() {

    private var list: List<Training> = emptyList()
    private var onItemClickListener: ((Training) -> Unit)? = null

    fun setList(list: List<Training>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setItemClickListener(onItemClickListener: ((Training) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrainingsBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class ViewHolder(val binding: ItemTrainingsBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(training: Training) {
            binding.tvTrainingName.text = training.title?.ru
            binding.tvDescription.text = training.description?.ru

            binding.ivEnableState.apply {
                background = when(training.title?.en) {
                    EnumTrainingName.Beginning.name -> {
                        ContextCompat.getDrawable(context, R.drawable.training_1)
                    }
                    EnumTrainingName.Children.name -> {
                        ContextCompat.getDrawable(context, R.drawable.training_2)
                    }
                    EnumTrainingName.Slimming.name -> {
                        ContextCompat.getDrawable(context, R.drawable.training_3)
                    }
                    else -> {
                        ContextCompat.getDrawable(context, R.drawable.training_4)
                    }
                }
            }

            binding.rlContainer.setOnClickListener {
                onItemClickListener?.invoke(training)
            }
        }
    }
}