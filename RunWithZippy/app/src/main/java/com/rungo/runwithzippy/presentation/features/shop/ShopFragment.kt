package com.rungo.runwithzippy.presentation.features.shop

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.badlogic.gdx.utils.Json
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.data.model.PaymentRequest
import com.rungo.runwithzippy.data.model.SkinRequest
import com.rungo.runwithzippy.data.model.Skins
import com.rungo.runwithzippy.databinding.ShopFragmentBinding
import com.rungo.runwithzippy.domain.adapters.ZippySkinsAdapter
import com.rungo.runwithzippy.presentation.features.config.InitialConfigModel
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.getViewModel

class ShopFragment : BaseFragment(), (View, Skins) -> Unit {

    private lateinit var binding: ShopFragmentBinding

    private val viewModel by lazy { getViewModel<ShopViewModel>() }
    private val initialConfigModel by lazy { getViewModel<InitialConfigModel>() }
    private lateinit var adapter: ZippySkinsAdapter
    private var choise_skin = ""
    private var apply_skin = false
    private var mRewardedAd: RewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(layoutInflater, R.layout.shop_fragment, container)
        return binding.root
    }

    override fun setupObservers() {
        val progressDialog = ProgressDialog(requireContext())
        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            if (it)
                progressDialog.show()
            else
                progressDialog.hide()
        })
    }

    override fun onAfterViewCreated() {
        setupEventListener(viewModel)
        initAds()
//        MobileAds.initialize(requireContext()) {}
        initSkins()
        initFragment()
    }

    private fun initAds(){
        var adRequest = AdRequest.Builder().build()
//ca-app-pub-3940256099942544/5224354917 тестовый токен
        //ca-app-pub-2530294564111476/3573554280
        RewardedAd.load(
            requireContext(),
            "ca-app-pub-5203994867346463/1056925637",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    println("ERROR == ")
                    println(adError?.message)
//                Log.d(TAG, adError?.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    println("Ad was loaded.")
//                Log.d(TAG, "Ad was loaded.")
                    mRewardedAd = rewardedAd
                }
            })
        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                println("Ad was dismissed.")
//                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                println("Ad failed to show.")
//                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                println("Ad showed fullscreen content.")
//                Log.d(TAG, "Ad showed fullscreen content.")
                // Called when ad is dismissed.
                // Don't set the ad reference to null to avoid showing the ad a second time.
                mRewardedAd = null
            }
        }
    }

    private fun initSkins(){
        if (this.isNetworkAvailable()) {
            viewModel.getSkins()
        } else {
            Toast.makeText(requireContext(), "Отсутствует интернет соединение", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> {
                val skins = viewModel.skins_data.value!!
                adapter = ZippySkinsAdapter(skins, this)
                binding.rvSkins.adapter = adapter
                binding.rvSkins.layoutManager = GridLayoutManager(requireContext(),3)
                binding.rvSkins.setHasFixedSize(true)
                adapter.notifyDataSetChanged()
                initialConfigModel.getInitialConfig()
                var current_apply_skin = initialConfigModel.initialConfig.value!!.user.skin
                for(i in 0..(skins.size-1)){
                    if(apply_skin){
                        if(choise_skin != "")
                            current_apply_skin = choise_skin
                        apply_skin = false
                    }
                    if (skins[i].id == current_apply_skin){
                        Picasso.get().load(skins[i].thumb_url).into(binding.ivCurrentSkin)
                        binding.tvCurrentSkinDescription.text = skins[i].description
                        binding.tvCurrentSkinCost.text = ""
                        binding.btnCurrentSkinChoise.visibility = View.GONE
                        binding.btnCurrentSkinBuy.visibility = View.GONE
                    }
                }
            }
            EventEnums.FAIL -> {
                Toast.makeText(requireContext(), eventData.eventPayload.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initFragment() {
        val sharedPrefences = this.requireActivity().getSharedPreferences(com.rungo.runwithzippy.utils.Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val access_token = sharedPrefences.getString(com.rungo.runwithzippy.utils.Constants.ACCESS_TOKEN,"").toString()
        binding.btnCurrentSkinChoise.setOnClickListener {
            viewModel.applySkin(SkinRequest(access_token, choise_skin))
            apply_skin = true
        }
        binding.btnCurrentSkinBuy.setOnClickListener {
            viewModel.buySkin(SkinRequest(access_token, choise_skin))
            apply_skin = false
        }
        binding.btnAds.setOnClickListener{
            if (mRewardedAd != null) {
                val adCallback = object: RewardedAdCallback() {
                    override fun onRewardedAdOpened() {
                        println("Ad opened.")
                        // Ad opened.
                    }
                    override fun onRewardedAdClosed() {
                        // Ad closed.
                        println("Ad closed.")
                    }
                    override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                        println("User посмотрел рекламу.")
                        viewModel.payment(PaymentRequest(access_token, Constants.PAYMENT_TYPE_ADS, ""))
                        // User earned reward.
                    }
                    override fun onRewardedAdFailedToShow(adError: AdError) {
                        println("Ad failed to display.")
                        // Ad failed to display.
                    }
                }
                mRewardedAd?.show(this.requireActivity(), adCallback)

//                mRewardedAd?.show(this.requireActivity(), OnUserEarnedRewardListener() {
//                    fun onUserEarnedReward(rewardItem: RewardItem) {
//                        var rewardAmount = rewardItem.amount
//                        var rewardType = rewardItem.getType()
//                        println("YES")
//                        println(rewardAmount)
//                        println("User earned the reward.")
////                        Log.d(TAG, "User earned the reward.")
//                    }
//                })
            } else {
                println("The rewarded ad wasn't ready yet.")
//                Log.d(TAG, "The rewarded ad wasn't ready yet.")
            }
        }
    }

    override fun invoke(p1: View, p2: Skins) {
        println(p2.thumb_url)
        println(p2.cost)
        println(p2.purchased)
        Picasso.get().load(p2.thumb_url).into(binding.ivCurrentSkin)
        binding.tvCurrentSkinDescription.text = p2.description
        binding.tvCurrentSkinCost.text = ""+p2.cost + " fishcoin"
        if(p2.purchased) {
            binding.btnCurrentSkinChoise.visibility = View.VISIBLE
            binding.btnCurrentSkinBuy.visibility = View.GONE
        } else {
            binding.btnCurrentSkinChoise.visibility = View.GONE
            binding.btnCurrentSkinBuy.visibility = View.VISIBLE
        }
        choise_skin = p2.id
        binding.layoutCurrentSkin.clearFocus()
        binding.layoutCurrentSkin.requestFocus()
//        Toast.makeText(requireContext(), )
    }

}