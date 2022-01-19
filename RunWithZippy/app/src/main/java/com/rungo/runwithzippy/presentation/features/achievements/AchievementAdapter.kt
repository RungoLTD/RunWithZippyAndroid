package com.rungo.runwithzippy.presentation.features.achievements

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class AchievementAdapter : BaseAdapter() {

    override fun getCount(): Int {
        return 0
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        return p1!!
    }

}