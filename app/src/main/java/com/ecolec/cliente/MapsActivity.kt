package com.ecolec.cliente

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ecolec.cliente.model.Recolector
import com.ecolec.cliente.retrofit.ApiRetrofit
import com.ecolec.cliente.retrofit.config.ConfigRetrofit
import com.ecolec.cliente.util.Setting
import com.ecolec.cliente.util.StatusBarUtil

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.bottom_sheet_map.*
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.PermissionRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    private val restApi = ConfigRetrofit.instance()

    companion object {
        const val CODE_RESULT_CAMERA = 101
        var byteArrayImage: ByteArray? = null
        const val RC_CAMERA_AND_LOCATION = 102
        const val UPDATE_INTERVAL = 5000f
        const val FASTEST_INTERVAL = 5000f
    }

    private var statusBack = 0
    private lateinit var mMap: GoogleMap

    private var latNow: Double = 0.0
    private var lonNow: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        StatusBarUtil.statusBarChange(window, Color.WHITE)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        var bottomsheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)
        bottomsheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomsheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(p0: View, p1: Int) {
            }
        })
        recyclerButton.setOnClickListener {

            if (papelCheck.isChecked || vidrioCheck.isChecked || plasticoCheck.isChecked || metalCheck.isChecked) {
                startActivityForResult(Intent(this, CameraActivity::class.java), CODE_RESULT_CAMERA)

            } else {
                Toast.makeText(this, "Debe seleccionar por lo menos una opcion" , Toast.LENGTH_SHORT).show()
            }

        }

        sendRecyclerButton.setOnClickListener {
            sendData()
        }

        dialog(this)
    }

    private fun sendData() {
        statusBack = 2
        progressView.visibility = View.VISIBLE
        val body = JsonObject()
        body.addProperty("id", Setting.id)
        body.addProperty("latitude", latNow)
        body.addProperty("longitude", lonNow)
        body.addProperty("photo", "https://remp")

        val categorias = JsonObject()
        categorias.addProperty("papel", papelCheck.isChecked)
        categorias.addProperty("vidrio", vidrioCheck.isChecked)
        categorias.addProperty("plastico", plasticoCheck.isChecked)
        categorias.addProperty("metal", metalCheck.isChecked)

        body.add("categorias", categorias)

        restApi.sendRecycler(body).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("RESPONSE_F", "${t}")
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.e("RESPONSE_SEND", "${response}")
                if (response.isSuccessful) {
                    checkView.visibility = View.VISIBLE
                    progressView.visibility = View.GONE
                    lastView.visibility = View.GONE
                    Thread {
                        Thread.sleep(2000)
                        runOnUiThread {
                            statusBack = 0
                            checkView.visibility = View.GONE
                        }
                    }.start()
                }
            }

        })
    }

    private fun getRecolectores() {
        restApi.getRecolector().enqueue(object : Callback<MutableList<Recolector>> {
            override fun onFailure(call: Call<MutableList<Recolector>>, t: Throwable) {

            }

            override fun onResponse(call: Call<MutableList<Recolector>>, response: Response<MutableList<Recolector>>) {
                Log.e("DATA_R", "${response}")
                if (response.isSuccessful) {

                    response.body()?.forEach {
                        mMap.addMarker(
                            MarkerOptions()
                                .position(LatLng(it.latitud, it.longitud))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                                .title("${it.nombres}")
                        )
                    }
                }
            }
        })
    }

    private fun dialog(activity: Activity) {
        val perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(activity, RC_CAMERA_AND_LOCATION, *perms)
                .setRationale("Debe activar el permiso de localización")
                .setPositiveButtonText("Si")
                .setNegativeButtonText("No")
                .build()
        )
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getRecolectores()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_RESULT_CAMERA && resultCode == Activity.RESULT_OK) {
            byteArrayImage?.let {
                statusBack = 1
                photoImage.setImageBitmap(BitmapFactory.decodeByteArray(it, 0, it.size))
                lastView.visibility = View.VISIBLE
            }
        }

        if (requestCode == 105 && resultCode == Activity.RESULT_OK) {

            SmartLocation.with(this).location()
                .start(object : OnLocationUpdatedListener {
                    override fun onLocationUpdated(p0: Location?) {
                        p0?.let {
                            val l = LatLng(it.latitude, it.longitude)
                            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(l, 13f)
                            mMap.animateCamera(cameraUpdate)
                        }

                    }
                })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private fun methodRequiresTwoPermission() {
        val perms = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            granted()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this@MapsActivity, "Debe activar el permiso de localización",
                RC_CAMERA_AND_LOCATION, *perms
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        granted()
    }

    private fun granted() {

        SmartLocation.with(this).location()
            .start(object : OnLocationUpdatedListener {
                override fun onLocationUpdated(p0: Location?) {
                    p0?.let {
                        latNow = it.latitude
                        lonNow = it.longitude
                        val l = LatLng(it.latitude, it.longitude)
                        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(l, 13f)
                        mMap.animateCamera(cameraUpdate)
                    }

                }
            })
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onBackPressed() {
        when (statusBack) {
            0 -> {
                super.onBackPressed()
            }
            1 -> {
                lastView.visibility = View.GONE
                statusBack = 0
            }
        }

    }
}
