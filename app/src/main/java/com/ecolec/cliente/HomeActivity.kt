package com.ecolec.cliente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        solicitarReciclador.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        FirebaseMessaging.getInstance().subscribeToTopic("Notifications")
    }
}
