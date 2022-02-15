package com.example.gramodaytask.utils.extensions

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


fun Context?.toast(
    stringId: Int,
    length: Int = Toast.LENGTH_LONG
) {
    this?.let {
        Toast.makeText(this, stringId, length)
            .show()
    }
}

fun Context?.toast(
    text: String,
    length: Int = Toast.LENGTH_LONG
) {
    this?.let {
        Toast.makeText(this, text, length)
            .show()
    }
}

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

fun <T> Context.openActivity(it: Class<T>) {
    val intent = Intent(this, it)
    startActivity(intent)
}

fun Context.openBrowserLink(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.shareLink(url: String, subject: String) {

    val intent = Intent(Intent.ACTION_SEND)

    intent.type = "text/plain"
    intent.putExtra(
        Intent.EXTRA_SUBJECT,
        subject
    )
    intent.putExtra(Intent.EXTRA_TEXT, url)
    startActivity(
        Intent.createChooser(
            intent,
            "Share via"
        )
    )
}

