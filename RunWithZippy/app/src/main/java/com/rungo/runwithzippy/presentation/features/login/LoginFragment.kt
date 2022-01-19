package com.rungo.runwithzippy.presentation.features.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.messaging.FirebaseMessaging
import com.rungo.runwithzippy.BuildConfig
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.data.model.AuthWithEmail
import com.rungo.runwithzippy.data.model.ProfileUpdateRequest
import com.rungo.runwithzippy.databinding.FragmentLoginBinding
import com.rungo.runwithzippy.presentation.containers.LoginContainer
import com.rungo.runwithzippy.presentation.containers.MainContainer
import com.rungo.runwithzippy.presentation.features.config.InitialConfigModel
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import com.rungo.runwithzippy.utils.extensions.showToast
import com.rungo.runwithzippy.utils.extensions.text
import org.koin.android.viewmodel.ext.android.getViewModel


class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel by lazy { getViewModel<LoginViewModel>() }
    private val initialConfigModel by lazy { getViewModel<InitialConfigModel>() }
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123
    private lateinit var callbackManager: CallbackManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(layoutInflater, R.layout.fragment_login, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        setupEventListener(viewModel)
        setupEventListener(initialConfigModel)


        val loginButton = binding.loginButton
        loginButton.setReadPermissions("email")
        // If using in a fragment
        // If using in a fragment
        loginButton.setFragment(this)

        // Callback registration
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                println("onSuccess")
                println(loginResult!!.accessToken)
            }

            override fun onCancel() {
                println("CANCEL")
            }

            override fun onError(exception: FacebookException) {
                println("CANCEL")
                println(exception)
            }
        })
        println("FACEBOOK")
        println(AccessToken.getCurrentAccessToken())
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    println("onSuccess2")
                    println(loginResult!!.accessToken)
                }

                override fun onCancel() {
                    println("CANCEL")
                }

                override fun onError(exception: FacebookException) {
                    println("CANCEL")
                    println(exception)
                }
            })
    }

    override fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            if ((requireActivity() as LoginContainer).isNetworkAvailable()) {
                if (!validate()) {
                    FirebaseMessaging.getInstance().token.addOnCompleteListener(
                        OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.w(
                                    "Firebase",
                                    "Fetching FCM registration token failed",
                                    task.exception
                                )
                                return@OnCompleteListener
                            }
                            // Get new FCM registration token
                            val token = task.result
                            viewModel.authWithEmail(
                                AuthWithEmail(
                                    binding.etPassword.text(),
                                    binding.etLogin.text(),
                                    token,
                                    0,
                                    BuildConfig.VERSION_CODE,
                                    BuildConfig.VERSION_NAME
                                )
                            )
                        })
                }
            } else {
                showToast("Отсутствует интернет соединение")
            }
        }
        binding.btnGoogle.setOnClickListener {
            if ((requireActivity() as LoginContainer).isNetworkAvailable()) {
                signIn()
//                val account = GoogleSignIn.getLastSignedInAccount(requireContext())
//                if(account != null)
//                    Toast.makeText(requireContext(),account.displayName,Toast.LENGTH_LONG).show()
//                else
//                    Toast.makeText(requireContext(),"NULL",Toast.LENGTH_LONG).show()
            }
        }
        binding.btnEndFirstLogin.setOnClickListener {
            if ((requireActivity() as LoginContainer).isNetworkAvailable()) {
                if (!validateFirstLogin()) {
                    val sharedPrefences = this.requireActivity()
                        .getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
                    val access_token = sharedPrefences.getString(Constants.ACCESS_TOKEN, "").toString()
                    viewModel.updateProfile(
                        ProfileUpdateRequest(
                            access_token,
                            binding.etUsername.text(),
                            "https://storage.yandexcloud.net/zippy-uploads/screenProfile.png",
                            binding.etTrophyName.text(),
                            binding.etRebukeName.text(),
                            binding.etHeight.text.toString().toInt(),
                            binding.etWeight.text.toString().toInt()
                        )
                    )
                    println("YESSS")
//                    viewModel.authWithEmail(AuthWithEmail(binding.etPassword.text(), binding.etLogin.text()))
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

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            Toast.makeText(requireContext(), account.displayName, Toast.LENGTH_LONG).show()
            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        println("requestCode = " + requestCode)
        println("resultCode = " + resultCode)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS_LOGIN -> {
                initialConfigModel.getInitialConfig()
            }
            EventEnums.SUCCESS -> {
                println("SUCCESS")
                println(initialConfigModel.initialConfig.value!!.user.name)
                if (initialConfigModel.initialConfig.value!!.user.name == "" || initialConfigModel.initialConfig.value!!.user.trophy_name == "" || initialConfigModel.initialConfig.value!!.user.rebuke_name == "" || initialConfigModel.initialConfig.value!!.user.weight == 0 || initialConfigModel.initialConfig.value!!.user.height == 0) {
                    binding.layoutLogin.visibility = View.GONE
                    binding.layoutAfterLogin.visibility = View.VISIBLE
                    binding.etUsername.setText(initialConfigModel.initialConfig.value!!.user.name)
                    binding.etTrophyName.setText(initialConfigModel.initialConfig.value!!.user.trophy_name)
                    binding.etRebukeName.setText(initialConfigModel.initialConfig.value!!.user.rebuke_name)
                    binding.etHeight.setText(initialConfigModel.initialConfig.value!!.user.height.toString())
                    binding.etWeight.setText(initialConfigModel.initialConfig.value!!.user.weight.toString())
                } else {
                    val intent = Intent(requireActivity(), MainContainer::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }

                println("SUCCESS 2")
            }
            EventEnums.FAIL -> {
                showToast(eventData.eventPayload as String)
            }
            EventEnums.OTHER -> {

            }
        }
    }

    private fun validate(): Boolean {
        return fieldsValidate(
            listOf(
                binding.etLogin to binding.tilLogin,
                binding.etPassword to binding.tilPassword
            )
        ).any { !it }
    }

    private fun validateFirstLogin(): Boolean {
        return fieldsValidateFirstLogin(
            listOf(
                binding.etUsername to binding.tilUsername,
                binding.etTrophyName to binding.tilTrophyName,
                binding.etRebukeName to binding.tilRebukeName,
                binding.etWeight to binding.tilWeight,
                binding.etHeight to binding.tilHeight
            )
        ).any { !it }
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

    private fun fieldsValidateFirstLogin(target: List<Pair<EditText, TextInputLayout>>): MutableList<Boolean> {
        val checkList = mutableListOf<Boolean>()
        target.forEach {
            when (it.first) {
                binding.etUsername -> {
                    when {
                        TextUtils.isEmpty(binding.etUsername.text()) -> {
                            it.second.error = getString(R.string.validate_empty_field)
                            checkList.add(false)
                        }
                        else -> {
                            it.second.error = null
                            checkList.add(true)
                        }
                    }
                }
                binding.etTrophyName -> {
                    when {
                        TextUtils.isEmpty(binding.etTrophyName.text()) -> {
                            it.second.error = getString(R.string.validate_empty_field)
                            checkList.add(false)
                        }
                        else -> {
                            it.second.error = null
                            checkList.add(true)
                        }
                    }
                }
                binding.etRebukeName -> {
                    when {
                        TextUtils.isEmpty(binding.etRebukeName.text()) -> {
                            it.second.error = getString(R.string.validate_empty_field)
                            checkList.add(false)
                        }
                        else -> {
                            it.second.error = null
                            checkList.add(true)
                        }
                    }
                }
                binding.etWeight -> {
                    when {
                        TextUtils.isEmpty(binding.etWeight.text()) -> {
                            it.second.error = getString(R.string.validate_empty_field)
                            checkList.add(false)
                        }
                        else -> {
                            if (binding.etWeight.text().equals("0")) {
                                it.second.error = "Вес не должен быть 0"
                                checkList.add(false)
                            } else {
                                it.second.error = null
                                checkList.add(true)
                            }
                        }
                    }
                }
                binding.etHeight -> {
                    when {
                        TextUtils.isEmpty(binding.etHeight.text()) -> {
                            it.second.error = getString(R.string.validate_empty_field)
                            checkList.add(false)
                        }
                        else -> {
                            if (binding.etHeight.text().equals("0")) {
                                it.second.error = "Рост не должен быть 0"
                                checkList.add(false)
                            } else {
                                it.second.error = null
                                checkList.add(true)
                            }
                        }
                    }
                }
            }
        }

        return checkList
    }
}