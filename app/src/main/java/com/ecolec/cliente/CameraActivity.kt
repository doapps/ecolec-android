package com.ecolec.cliente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.camerakit.CameraKitView
import com.jpegkit.Jpeg
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        captureImage.setOnClickListener {
            cameraKitView.captureImage(object: CameraKitView.ImageCallback{
                override fun onImage(p0: CameraKitView?, p1: ByteArray?) {
                    Thread {
                        val jpeg = p1?.let { it1 -> Jpeg(it1) }
                        /*
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setJpeg(jpeg);
                            }
                        });
                        */

                    }.start()
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
