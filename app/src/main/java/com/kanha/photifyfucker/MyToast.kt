package com.kanha.savefile

import android.content.Context
import android.widget.Toast

object MyToast {
    private var toast: Toast? = null
    fun show(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        if (toast != null) {
            toast!!.cancel()
        }
        toast = Toast.makeText(context, message, duration)
        toast!!.show()
    }
}