package com.example.gramodaytask.utils.dialog

import android.app.Dialog

interface DialogShower {
    fun show(
        dialog: AppDialogs,
        vararg clickListeners: (() -> Unit)
    )

    fun create(
        dialog: AppDialogs,
        vararg clickListeners: (() -> Unit)
    ): Dialog
}
