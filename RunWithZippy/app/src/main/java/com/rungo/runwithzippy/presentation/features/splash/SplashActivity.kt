package com.rungo.runwithzippy.presentation.features.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.presentation.containers.LoginContainer
import com.rungo.runwithzippy.presentation.containers.MainContainer
import com.rungo.runwithzippy.presentation.containers.RegistrationContainer
import com.rungo.runwithzippy.presentation.features.config.FcmTokenModel
import com.rungo.runwithzippy.presentation.features.config.InitialConfigModel
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.viewmodel.ext.android.getViewModel

class SplashActivity : BaseActivity() {

    private val viewModel by lazy { getViewModel<SplashViewModel>() }
    private val initialConfigModel by lazy { getViewModel<InitialConfigModel>() }
    private val fcmTokenModel by lazy { getViewModel<FcmTokenModel>() }

    var hello_text: Array<String> = arrayOf("Спортик", "Марафонец", "Любитель побегать", "Сделаем тело к лету?")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupEventListener(this, initialConfigModel)
        if (viewModel.isAccessTokenAvailable()) {
            if (this.isNetworkAvailable()) {
                initialConfigModel.getInitialConfig()
            } else {
                Toast.makeText(this, "Отсутствует интернет соединение", Toast.LENGTH_SHORT).show()
            }
        } else {
            main_splash_layout.visibility = View.GONE
            no_auth_splash_layout.visibility = View.VISIBLE
            hello_text_changed.text = hello_text.get(0)
            splash_sign_in_btn.setOnClickListener(View.OnClickListener {
                val intent = Intent(this, LoginContainer::class.java)
                startActivity(intent)
            })
            splash_sign_up_btn.setOnClickListener(View.OnClickListener {
                val intent = Intent(this, RegistrationContainer::class.java)
                startActivity(intent)
            })
        }
//        openNextScreen()
    }

    override fun exit() {

    }

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> {
                if (initialConfigModel.initialConfig.value?.user?.fcm_token == "" || initialConfigModel.initialConfig.value?.user?.fcm_token == null){
                    FirebaseMessaging.getInstance().token.addOnCompleteListener(
                        OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.w("Firebase", "Fetching FCM registration token failed", task.exception)
                                return@OnCompleteListener
                            }
                            // Get new FCM registration token
                            val token = task.result
                            if(token != ""){
                                fcmTokenModel.setFcmToken(token, initialConfigModel)
                            }
                        })
                } else {
                    val intent = Intent(this, MainContainer::class.java)
                    startActivity(intent)
                }
            }
            EventEnums.FAIL -> {
                Toast.makeText(this, "Не удалось получить данные", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openNextScreen() {
//        if (viewModel.isAccessTokenAvailable()) {
//            val intent = Intent(this, MainContainer::class.java)
//            startActivity(intent)
//        } else {
//            val intent = Intent(this, LoginContainer::class.java)
//            startActivity(intent)
////        }
//        finish()
    }
}