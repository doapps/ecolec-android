package com.ecolec.cliente.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ToolbarUtil {
    companion object {
        fun setUpToolbar(activity: AppCompatActivity, toolbar: Toolbar, title: String) {
            activity.apply {
                setSupportActionBar(toolbar)
                supportActionBar!!.title = title
            }
        }
    }
}