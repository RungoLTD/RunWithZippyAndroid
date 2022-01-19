package com.rungo.runwithzippy.presentation.features.running

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.data.model.RunningStatistic
import org.threeten.bp.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RunningStatisticsAdapter(
    private val values: MutableList<RunningStatistic>?,
    private val onClickListener: (View, RunningStatistic) -> Unit
) : RecyclerView.Adapter<RunningStatisticsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_running_statistics, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println(values!!.get(position))
        val item = values!!.get(position)
        holder.tv_item_running_name.text = item.name
        val format_create = SimpleDateFormat("dd.MM, HH:mm")
        holder.tv_item_running_date.text = format_create.format(item.created)
        holder.tv_item_running_distance.text = "%.2f".format(item.meters * 0.001) + " км"
        val hour = item.time / 3600
        val minute = (item.time % 3600) / 60
        val second = item.time % 60
        if(hour == 0L)
            holder.tv_item_running_time.text = String.format("%02d:%02d", minute, second)
        else
            holder.tv_item_running_time.text = String.format("%02d:%02d:%02d", hour, minute, second)
        holder.tv_item_running_temp.text = ""+item.avgPace
        holder.ll_item_statistics_list?.setOnClickListener { view ->
            onClickListener.invoke(view, item)
        }
    }

    override fun getItemCount(): Int = values!!.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_item_running_name: TextView = view.findViewById(R.id.tv_item_running_name)
        val tv_item_running_date: TextView = view.findViewById(R.id.tv_item_running_date)
        val tv_item_running_distance: TextView = view.findViewById(R.id.tv_item_running_distance)
        val tv_item_running_time: TextView = view.findViewById(R.id.tv_item_running_time)
        val tv_item_running_temp: TextView = view.findViewById(R.id.tv_item_running_temp)
        val ll_item_statistics_list: LinearLayout = view.findViewById(R.id.ll_item_statistics_list)
    }
}