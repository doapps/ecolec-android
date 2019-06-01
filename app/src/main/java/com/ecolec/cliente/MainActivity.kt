package com.ecolec.cliente

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ecolec.cliente.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.statusBarChange(window, Color.WHITE)
        registerUser.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        signInUser.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
