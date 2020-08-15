package com.rungo.runwithzippy.presentation.features.login

import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.data.model.AuthWithEmail
import com.rungo.runwithzippy.databinding.FragmentLoginBinding
import com.rungo.runwithzippy.presentation.containers.LoginContainer
import com.rungo.runwithzippy.presentation.containers.MainContainer
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import com.rungo.runwithzippy.utils.extensions.showToast
import com.rungo.runwithzippy.utils.extensions.text
import org.koin.android.viewmodel.ext.android.getViewModel

class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel by lazy { getViewModel<LoginViewModel>() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(layoutInflater, R.layout.fragment_login, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        setupEventListener(viewModel)
    }

    override fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            if ((requireActivity() as LoginContainer).isNetworkAvailable()) {
                if (!validate()) {
                    viewModel.authWithEmail(AuthWithEmail(binding.etPassword.text(), binding.etLogin.text()))
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

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> {
                val intent = Intent(requireActivity(), MainContainer::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            EventEnums.FAIL -> {
                showToast(eventData.eventPayload as String)
            }
            EventEnums.OTHER -> {

            }
        }
    }

    private fun validate(): Boolean {
        return fieldsValidate(listOf(binding.etLogin to binding.tilLogin, binding.etPassword to binding.tilPassword)).any { !it }
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
                binding.etPassword -> {
                    when {
                        TextUtils.isEmpty(binding.etPassword.text()) -> {
                            it.second.error = getString(R.string.validate_empty_field)
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
}