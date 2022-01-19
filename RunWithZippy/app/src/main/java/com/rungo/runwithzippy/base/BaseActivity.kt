package com.rungo.runwithzippy.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationBase
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.rungo.runwithzippy.utils.ConnectionLiveData
import com.rungo.runwithzippy.utils.EventData
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    protected lateinit var connectionLiveData: ConnectionLiveData

    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<T> = lazy { DataBindingUtil.setContentView<T>(this, resId) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)
    }

    fun setupEventListener(lifecycleOwner: LifecycleOwner, viewModel: BaseViewModel) {
        viewModel.events.observe(lifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                Timber.d("EVENT FIRED: ${it.eventCode}")
                onEvent(it)
            }
        })
    }

    open fun onEvent(eventData: EventData) {}
}