package com.ecolec.cliente

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ecolec.cliente.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        StatusBarUtil.statusBarChange(window, Color.WHITE)
        register.setOnClickListener{
            startActivity(Intent(this, MapsActivity::class.java))
            finish()
        }
    }
}
