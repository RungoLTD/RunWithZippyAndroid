package com.rungo.runwithzippy.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AppActivity
import com.rungo.runwithzippy.utils.ConnectionLiveData
import com.rungo.runwithzippy.utils.EventData
import timber.log.Timber

abstract class BaseActivity : AppActivity() {

    protected lateinit var connectionLiveData: ConnectionLiveData

    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<T> = lazy { DataBindingUtil.setContentView<T>(this, resId) }

    fun getInitializeForView(listener: ApplicationListener, config: AndroidApplicationConfiguration): View {
        return this.initializeForView(listener, config)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)
    }
}