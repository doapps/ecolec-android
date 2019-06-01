package com.ecolec.cliente

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ecolec.cliente.util.StatusBarUtil
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        StatusBarUtil.statusBarChange(window, Color.WHITE)
        solicitarReciclador.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        acopiosButton.setOnClickListener {
            startActivity(Intent(this, MLCameraActivity::class.java))
        }

        FirebaseMessaging.getInstance().subscribeToTopic("Notifications")
    }
}
