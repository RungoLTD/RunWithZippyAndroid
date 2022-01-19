package com.rungo.runwithzippy.presentation.dialogs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.rungo.runwithzippy.BuildConfig
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.presentation.containers.LoginContainer
import com.rungo.runwithzippy.presentation.features.config.InitialConfigModel
import com.rungo.runwithzippy.presentation.features.splash.SplashActivity
import com.rungo.runwithzippy.utils.Constants
import kotlinx.android.synthetic.main.settings_dialog.*
import org.koin.android.viewmodel.ext.android.getViewModel


class SettingsDialog(private val callbackListener: CallbackListener, private val context1: Context) : DialogFragment() {
    private val initialConfigModel by lazy { getViewModel<InitialConfigModel>() }

    val TAG = "example_dialog"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.settings_dialog, container, false)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener{dismiss()}
        toolbar.setTitle("Настройки")
        toolbar.inflateMenu(R.menu.setting_dialog_menu)
        toolbar.setOnMenuItemClickListener{
            when(it.itemId) {
                R.id.action_save -> println("SAVE")
            }
            true
        }
        etSettingTrophyName.setText(initialConfigModel.initialConfig.value!!.user.trophy_name)
        val values : Array<String> = arrayOf(
            "Запустить инструкцию",
            "Условия пользования",
            "О версии",
            "Наш блог",
            "Связаться с нами",
            "Выйти с аккаунта"
        )
        val adapter = ArrayAdapter<String>(
            this.context1,
            android.R.layout.simple_list_item_1,
            values
        )
        rvSettingOther.adapter = adapter
        rvSettingOther.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the selected item text from ListView
            val selectedItem = parent.getItemAtPosition(position) as String
            when(selectedItem){
                "Условия пользования" -> {

                }
                "О версии" -> {
                    val versionCode: Int = BuildConfig.VERSION_CODE
                    val versionName: String = BuildConfig.VERSION_NAME
                    Toast.makeText(this.context1, "Версия "+versionName+" сборка "+versionCode, Toast.LENGTH_LONG).show()
                }
                "Наш блог" -> {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.myrungo.com/blog")
                    )
                    startActivity(browserIntent)
                }
                "Связаться с нами" -> {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.myrungo.com/about-us")
                    )
                    startActivity(browserIntent)
                }
                "Выйти с аккаунта" -> {
                    //TODO надо доделать выход с аккаунта
                    val sharedPrefences = this.requireActivity()
                        .getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
                    sharedPrefences.edit().putString(Constants.ACCESS_TOKEN, "").apply()
                    val intent = Intent(this.context1, SplashActivity::class.java)
                    startActivity(intent)
                }
            }
//            selectedItem
//            val intent = Intent(this, BookDetailActivity::class.java)
//            startActivity(intent)
        }
    }
}