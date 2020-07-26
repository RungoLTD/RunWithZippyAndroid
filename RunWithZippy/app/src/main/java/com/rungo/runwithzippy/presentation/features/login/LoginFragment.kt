package com.rungo.runwithzippy.presentation.features.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentLoginBinding
import com.rungo.runwithzippy.presentation.containers.MainContainer

class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_login, container)
        return binding.root
    }

    override fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(requireActivity(), MainContainer::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}