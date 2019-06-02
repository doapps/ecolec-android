package com.ecolec.cliente

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ecolec.cliente.session.Preference
import com.ecolec.cliente.util.StatusBarUtil
import com.ecolec.cliente.util.ToolbarUtil
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar.view.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var instancePreference: Preference = Preference.instance(context = this)
        StatusBarUtil.statusBarChange(window, Color.WHITE)
        titleUser.text = "Bienvenido ${instancePreference.nameUser}"
        solicitarReciclador.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        acopiosButton.setOnClickListener {
            startActivity(Intent(this, MLCameraActivity::class.java))
        }

        FirebaseMessaging.getInstance().subscribeToTopic("Notifications")
    }
}
