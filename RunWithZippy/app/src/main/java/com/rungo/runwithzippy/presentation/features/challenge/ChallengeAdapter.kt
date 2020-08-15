package com.rungo.runwithzippy.presentation.features.challenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rungo.runwithzippy.data.model.Challenge
import com.rungo.runwithzippy.databinding.ItemChallengesBinding
import com.rungo.runwithzippy.utils.extensions.downloadImageToView

class ChallengeAdapter : RecyclerView.Adapter<ChallengeAdapter.ViewHolder>() {

    private var challengeList: List<Challenge> = listOf()

    fun setList(challengeList: List<Challenge>) {
        this.challengeList = challengeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChallengesBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = challengeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(challengeList[position])
    }

    inner class ViewHolder(val binding: ItemChallengesBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(challenge: Challenge) {
            binding.tvTrainingName.text = challenge.title_ru
            binding.tvDescription.text = challenge.description_ru
            binding.tvFishCoin.text = challenge.reward.toString()

            if (challenge.isActive) {
                binding.ivEnableState.downloadImageToView(challenge.active_image_url)
            } else {
                binding.ivEnableState.downloadImageToView(challenge.disabled_image_url)
            }
        }
    }
}