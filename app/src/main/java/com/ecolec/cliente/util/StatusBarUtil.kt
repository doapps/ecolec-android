package com.ecolec.cliente.util

import android.os.Build
import android.view.View
import android.view.Window

class StatusBarUtil {
    companion object {
        fun statusBarChange(window: Window, color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = window
                window.apply {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    statusBarColor = color
                }
            }
        }

    }
}