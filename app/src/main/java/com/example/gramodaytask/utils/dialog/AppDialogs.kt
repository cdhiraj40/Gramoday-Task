package com.example.gramodaytask.utils.dialog

import android.view.View
import com.example.gramodaytask.R

sealed class AppDialogs(
    val title: Int?,
    val message: Int?,
    val positiveMessage: Int,
    val negativeMessage: Int?,
    val cancelable: Boolean = true,
    val icon: Int? = null,
    val neutralMessage: Int? = null,
    val getView: (() -> View)? = null
) {
    object DeleteRepository : AppDialogs(
        title = R.string.delete_repository_dialog_title,
        message = R.string.delete_repository_dialog_message,
        positiveMessage = R.string.yes,
        negativeMessage = android.R.string.cancel,
        cancelable = true,
        icon = R.drawable.ic_baseline_delete_forever_24
    )

    interface HasBodyFormatArgs {
        val args: List<Any>
    }
}
