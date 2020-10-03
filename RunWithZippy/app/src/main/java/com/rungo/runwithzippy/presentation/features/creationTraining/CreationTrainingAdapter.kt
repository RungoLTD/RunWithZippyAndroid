package com.rungo.runwithzippy.presentation.features.creationTraining

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.rungo.runwithzippy.data.model.Options
import com.rungo.runwithzippy.databinding.LayoutCreationTrainingBinding

class CreationTrainingAdapter : PagerAdapter() {

    private var list: List<Options> = listOf()
    private var onItemClickListener: (() -> Unit)? = null

    fun setList(list: List<Options>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setItemClickListener(onItemClickListener: (() -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = list.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = LayoutCreationTrainingBinding.inflate(LayoutInflater.from(container.context), container, false)
        val adapter = CreationItemAdapter()

        binding.tvSubtitle.text = list[position].title?.ru
        binding.rvButtons.adapter = adapter
        adapter.setList(list[position].options)
        adapter.setItemClickListener {
            onItemClickListener?.invoke()
        }

        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}