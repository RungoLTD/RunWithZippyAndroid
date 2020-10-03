package com.rungo.runwithzippy.presentation.features.creationTraining

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rungo.runwithzippy.data.model.Options
import com.rungo.runwithzippy.data.model.OptionsV2
import com.rungo.runwithzippy.data.model.Training
import com.rungo.runwithzippy.databinding.ItemButtonBinding

class CreationItemAdapter : RecyclerView.Adapter<CreationItemAdapter.ViewHolder>() {

    private var list: List<OptionsV2> = listOf()

    private var onItemClickListener: (() -> Unit)? = null

    fun setList(list: List<OptionsV2>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setItemClickListener(onItemClickListener: (() -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class ViewHolder(private val binding: ItemButtonBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(options: OptionsV2) {
            binding.btnItem.text = options.title?.ru

            binding.btnItem.setOnClickListener {
                onItemClickListener?.invoke()
            }
        }
    }
}