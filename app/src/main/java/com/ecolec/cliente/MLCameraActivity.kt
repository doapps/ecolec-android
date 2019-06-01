package com.ecolec.cliente

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.camerakit.CameraKitView
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_mlcamera.*
import kotlinx.android.synthetic.main.activity_mlcamera.cameraKitView
import kotlinx.android.synthetic.main.activity_mlcamera.captureImage

class MLCameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mlcamera)
        captureImage.setOnClickListener {
            cameraKitView.captureImage(object : CameraKitView.ImageCallback {
                override fun onImage(p0: CameraKitView?, p1: ByteArray?) {
                    Log.e("DATA", "TRUE")
                    runOnUiThread {
                        val returnIntent = Intent()
                        MapsActivity.byteArrayImage = p1
                        setResult(Activity.RESULT_OK, returnIntent)
                    }

                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        cameraKitView.onStart()
    }

    override fun onResume() {
        super.onResume()
        cameraKitView.onResume()
    }

    override fun onPause() {
        super.onPause()
        cameraKitView.onPause()
    }

    override fun onStop() {
        super.onStop()
        cameraKitView.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
