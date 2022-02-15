package com.example.gramodaytask.utils.extensions

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Fragment.closeKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun View.closeKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun showSnackBar(activity: Activity, message: String?) {
    val rootView = activity.window.decorView.findViewById<View>(android.R.id.content)
    val snackbar = Snackbar.make(rootView, message!!, Snackbar.LENGTH_SHORT)
    snackbar.show()
}

fun showSnackBarWithAction(
    activity: Activity,
    message: String?,
    actionRes: String,
    color: Int? = null,
    listener: (View) -> Unit
) {
    val rootView = activity.window.decorView.findViewById<View>(android.R.id.content)
    val snackbar = Snackbar.make(rootView, message!!, Snackbar.LENGTH_SHORT)
    snackbar.setAction(actionRes, listener)
    snackbar.show()
}

