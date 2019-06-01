package com.ecolec.cliente

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.ecolec.cliente.retrofit.config.ConfigRetrofit
import com.ecolec.cliente.util.Setting
import com.ecolec.cliente.util.StatusBarUtil
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val signIn = ConfigRetrofit.instance()
    val body = JsonObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.statusBarChange(window, Color.WHITE)
        registerUser.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        signInUser.setOnClickListener {
            if (TextUtils.isEmpty(email.text)) {
                Toast.makeText(this, "El campo email debe estar completo.", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(password.text)) {
                Toast.makeText(this, "El campo password debe estar completo.", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this, OnSuccessListener {
                    val newToken = it.token
                    Log.e("newToken", newToken)
                    body.addProperty("email", email.text.toString())
                    body.addProperty("password", password.text.toString())
                    body.addProperty("token", newToken)
                    signIn.signInUser(body).enqueue(object :
                        Callback<JsonObject> {
                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            if (response.isSuccessful) {
                                Setting.id = response.body()?.asJsonObject?.get("id")?.asInt ?: 0
                                startActivity(Intent(applicationContext, MapsActivity::class.java))
                            } else {
                                val errorBody = JsonParser().parse(response.errorBody()?.string()).asJsonObject
                                Toast.makeText(applicationContext, errorBody["message"].asString, Toast.LENGTH_SHORT).show()
                            }

                        }
                    })
                })

            }
        }



    }
}
