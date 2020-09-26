package com.rungo.runwithzippy.presentation.features.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.rungo.runwithzippy.data.model.Training
import com.rungo.runwithzippy.databinding.ItemTrainingsBinding

class MarathonAdapter : PagerAdapter() {

    private var list: List<Training> = listOf()

    fun setList(list: List<Training>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int  = list.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemTrainingsBinding.inflate(LayoutInflater.from(container.context))



        return binding.root
    }
}