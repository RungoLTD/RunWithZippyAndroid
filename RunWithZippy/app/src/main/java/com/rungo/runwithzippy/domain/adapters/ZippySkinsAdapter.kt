package com.rungo.runwithzippy.domain.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.data.model.Skins
import com.squareup.picasso.Picasso

class ZippySkinsAdapter (private val skinList: List<Skins>, private val onClickListener: (View, Skins) -> Unit) : RecyclerView.Adapter<ZippySkinsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_skins, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = skinList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val skin = skinList[position]
        println("thumb_url = "+skin.thumb_url)
        Picasso.get().load(skin.thumb_url).into(holder.imageViewSkin)
        println(holder.imageViewSkin)
        holder.imageViewSkin?.setOnClickListener { view ->
            onClickListener.invoke(view, skin)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewSkin: ImageView? = null

        init {
            imageViewSkin = itemView.findViewById(R.id.img_skin_1)
        }

    }

}