package com.rungo.runwithzippy.utils.extensions

import android.app.AlertDialog
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import timber.log.Timber

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun EditText.text(): String {
    return this.text.toString()
}

fun ImageView.downloadImageToView(url: String?, onSuccessFunction: (() -> Unit)? = null, onErrorFunction: (() -> Unit)? = null) {
    if (url.isNullOrEmpty()) return
    Picasso
        .get()
        .load(url)
        .into(this, object: com.squareup.picasso.Callback {
            override fun onSuccess() {
                Timber.d("Success loading image from url = '$url'")
                onSuccessFunction?.invoke()
            }

            override fun onError(e: java.lang.Exception?) {
                Timber.d("Error loading image from url = '$url'")
                onErrorFunction?.invoke()
            }
        })
}
