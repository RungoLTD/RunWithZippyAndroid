package com.rungo.runwithzippy.presentation.features.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.data.model.Training
import com.rungo.runwithzippy.databinding.ItemTrainingsBinding
import com.rungo.runwithzippy.utils.EnumTrainingName

class MarathonAdapter : PagerAdapter() {

    private var list: List<Training> = emptyList()

    fun setList(list: List<Training>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, any: Any): Boolean = view == any

    override fun getCount(): Int = list.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemTrainingsBinding.inflate(LayoutInflater.from(container.context))

        binding.tvTrainingName.text = list[position].title?.ru
        binding.tvDescription.text = list[position].description?.ru
        binding.ivEnableState.background = ContextCompat.getDrawable(container.context, R.drawable.training_4)

        container.addView(binding.root)

        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }
}