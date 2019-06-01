package com.ecolec.cliente

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ecolec.cliente.session.Preference
import com.ecolec.cliente.util.Setting
import com.ecolec.cliente.util.StatusBarUtil

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        StatusBarUtil.statusBarChange(window, Color.WHITE)
        var instancePreference: Preference = Preference.instance(context = this)
        Handler().postDelayed({
            if (instancePreference.nameUser == "") {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }, Setting.time_splash_screen)
    }
}
