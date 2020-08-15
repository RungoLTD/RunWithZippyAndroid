package com.rungo.runwithzippy.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.utils.EventData
import timber.log.Timber

abstract class BaseFragment : Fragment() {
    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes resId: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate(inflater, resId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        onAfterViewCreated()
    }

    open fun setupObservers() {}

    open fun setupListeners() {}

    open fun onAfterViewCreated() {}

    fun setupEventListener(viewModel: BaseViewModel) {
        viewModel.events.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                Timber.d("EVENT FIRED: ${it.eventCode}")
                onEvent(it)
            }
        })
    }

    open fun onEvent(eventData: EventData) {}
}