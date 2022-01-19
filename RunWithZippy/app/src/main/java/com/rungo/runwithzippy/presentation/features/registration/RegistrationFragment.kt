package com.rungo.runwithzippy.presentation.features.registration

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.messaging.FirebaseMessaging
import com.rungo.runwithzippy.BuildConfig
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.data.model.AuthWithEmail
import com.rungo.runwithzippy.data.model.RegWithEmail
import com.rungo.runwithzippy.databinding.FragmentRegistrationBinding
import com.rungo.runwithzippy.presentation.containers.LoginContainer
import com.rungo.runwithzippy.presentation.containers.MainContainer
import com.rungo.runwithzippy.presentation.containers.RegistrationContainer
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import com.rungo.runwithzippy.utils.extensions.showToast
import com.rungo.runwithzippy.utils.extensions.text
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber

class RegistrationFragment : BaseFragment() {

    private lateinit var binding: FragmentRegistrationBinding

    private val viewModel by lazy { getViewModel<RegistrationViewModel>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_registration, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        setupEventListener(viewModel)
    }

    override fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            if ((requireActivity() as RegistrationContainer).isNetworkAvailable()) {
                if (!validate()) {
                    FirebaseMessaging.getInstance().token.addOnCompleteListener(
                        OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.w("Firebase", "Fetching FCM registration token failed", task.exception)
                                return@OnCompleteListener
                            }
                            // Get new FCM registration token
                            val token = task.result
                            viewModel.regWithEmail(
                                RegWithEmail(
                                    binding.etLogin.text(),
                                0,
                                    BuildConfig.VERSION_CODE,
                                    BuildConfig.VERSION_NAME,
                                    token
                                )
                            )
                        })

                }
            } else {
                showToast("Отсутствует интернет соединение")
            }
        }
    }

    override fun setupObservers() {
        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
    }

    private fun validate(): Boolean {
        return fieldsValidate(listOf(binding.etLogin to binding.tilLogin)).any { !it }
    }

    private fun fieldsValidate(target: List<Pair<EditText, TextInputLayout>>): MutableList<Boolean> {
        val checkList = mutableListOf<Boolean>()
        target.forEach {
            when (it.first) {
                binding.etLogin -> {
                    when {
                        TextUtils.isEmpty(binding.etLogin.text()) -> {
                            it.second.error = getString(R.string.validate_empty_field)
                            checkList.add(false)
                        }
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etLogin.text())
                            .matches() -> {
                            it.second.error = getString(R.string.validate_email)
                            checkList.add(false)
                        }
                        else -> {
                            it.second.error = null
                            checkList.add(true)
                        }
                    }
                }
            }
        }

        return checkList
    }

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setMessage("Мы прислали Вам пароль по email")
                alertDialogBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    val intent = Intent(requireContext(), LoginContainer::class.java)
                    startActivity(intent)
                }
                alertDialogBuilder.show()
            }
            EventEnums.FAIL -> {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setMessage("Такой email уже используется другим пользователем. Если это ваш email можете сразу войти")
                alertDialogBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    dialog.dismiss()
                }
                alertDialogBuilder.show()
            }
            EventEnums.OTHER -> {
                Timber.d("ПАХАЙ 2")
            }
        }
    }
}