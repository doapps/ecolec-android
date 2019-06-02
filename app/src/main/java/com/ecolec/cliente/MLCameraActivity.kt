package com.ecolec.cliente

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.camerakit.CameraKitView
import com.ecolec.cliente.util.StatusBarUtil
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions
import kotlinx.android.synthetic.main.activity_mlcamera.*
import kotlinx.android.synthetic.main.activity_mlcamera.cameraKitView
import kotlinx.android.synthetic.main.activity_mlcamera.captureImage
import java.lang.Exception

class MLCameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mlcamera)
        StatusBarUtil.statusBarChange(window,  Color.BLACK)
        closeImage.setOnClickListener {
            dialogView.visibility = View.GONE
        }

        botarButton.setOnClickListener {
            startActivity(Intent(this@MLCameraActivity, Maps2Activity::class.java))
        }

        intentarButton.setOnClickListener {
            dialogView.visibility = View.GONE
        }
        captureImage.setOnClickListener {
            cameraKitView.captureImage(object : CameraKitView.ImageCallback {
                override fun onImage(p0: CameraKitView?, p1: ByteArray?) {
                    Log.e("DATA", "TRUE")

                    val options =  FirebaseVisionLabelDetectorOptions.Builder()
                        .setConfidenceThreshold(0.7f)
                        .build();
                    val image = FirebaseVisionImage.fromBitmap(BitmapFactory.decodeByteArray(p1, 0, p1?.size ?: 0))
                    val detector = FirebaseVision.getInstance().getVisionLabelDetector(options)

                    detector.detectInImage(image).addOnSuccessListener(object :  OnSuccessListener<MutableList<FirebaseVisionLabel>>{
                        override fun onSuccess(p0: MutableList<FirebaseVisionLabel>?) {
                            p0?.let {
                                if (it.size > 0){

                                    var labelShow = ""
                                    var categoryShow = ""
                                    it.forEach {
                                        Log.e("item_it", " - ${it.label}")
                                        if (it.label.equals("Cup")){
                                            labelShow = it.label
                                            categoryShow = "Carton"
                                        } else if (it.label.equals("Paper")){
                                            labelShow = it.label
                                            categoryShow = "Papel"
                                        } else if (it.label.equals("Cola")){
                                            labelShow = it.label
                                            categoryShow = "Plastico"
                                        }

                                        else {
                                            labelShow = it.label
                                            categoryShow = "Indefinido"
                                        }
                                    }

                                    if(categoryShow.equals("Indefinido")){
                                        intentarButton.visibility = View.VISIBLE
                                        botarButton.visibility = View.GONE
                                        categoryShow = "un objeto no registrado"
                                    } else {
                                        intentarButton.visibility = View.GONE
                                        botarButton.visibility = View.VISIBLE
                                    }

                                    //Toast.makeText(this@MLCameraActivity, "${labelShow} - $categoryShow", Toast.LENGTH_SHORT).show()
                                    messageText.text = "¡ Has encontrado ${categoryShow} !"
                                    dialogView.visibility = View.VISIBLE
                                }
                            }
                        }

                    }).addOnFailureListener(object :  OnFailureListener {
                        override fun onFailure(p0: Exception) {
                            Log.e("FAILURE", " - ${p0}")
                        }
                    })

                    runOnUiThread {

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
