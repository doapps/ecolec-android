package com.ecolec.cliente

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_map.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val CODE_RESULT_CAMERA = 101
        var byteArrayImage : ByteArray? = null
    }

    private var statusBack = 0
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
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
            startActivityForResult(Intent(this, CameraActivity::class.java), CODE_RESULT_CAMERA)
        }
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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_RESULT_CAMERA && resultCode == Activity.RESULT_OK){
            byteArrayImage?.let {
                statusBack = 1
                photoImage.setImageBitmap(BitmapFactory.decodeByteArray(it, 0, it.size))
                lastView.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
       when(statusBack){
           0-> {
               super.onBackPressed()
           }
           1 -> {
               lastView.visibility = View.GONE
               statusBack = 0
           }
       }

    }
}
