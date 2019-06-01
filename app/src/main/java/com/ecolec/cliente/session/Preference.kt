package com.ecolec.cliente.session

import android.content.Context
import android.content.SharedPreferences

object Preference {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private const val namePreference = "Ecolec"
    private const val nameUserPreference = "nameUser"
    private const val idUserPreference = "idUser"

    fun instance(context: Context): Preference {
        sharedPreferences = context.getSharedPreferences(namePreference, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        return this
    }

    var nameUser: String?
        get() = sharedPreferences.getString(nameUserPreference, "")
        set(value) {
            editor.putString(nameUserPreference, value).commit()
        }

    var idUser: Int
        get() = sharedPreferences.getInt(idUserPreference, -1)
        set(value) {
            editor.putInt(idUserPreference, value).commit()
        }
}