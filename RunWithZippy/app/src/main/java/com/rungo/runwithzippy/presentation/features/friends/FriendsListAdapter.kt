package com.rungo.runwithzippy.presentation.features.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.data.model.Friend

class FriendsListAdapter (
    private val friendsList: List<Friend>,
    private val onClickListener: (View, Friend) -> Unit) :
    RecyclerView.Adapter<FriendsListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friends, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = friendsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("model friend")
        println(friendsList.get(position))
        val friend1 = friendsList.get(position)
        holder.friendName.text = friend1.name
        holder.friendMood.text = "Настр: "+friend1.mood
        holder.ll_item_friends_list?.setOnClickListener { view ->
            onClickListener.invoke(view, friend1)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var friendName: TextView = itemView.findViewById(R.id.item_friend_name)
        var friendMood: TextView = itemView.findViewById(R.id.item_friend_mood)
        var ll_item_friends_list: LinearLayoutCompat = itemView.findViewById(R.id.ll_item_friends_list)
    }

}