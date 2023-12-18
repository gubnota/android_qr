package net.fenki.qr
//package com.example.barcodescanner


//import com.google.android.gms.vision.CameraSource
//import com.google.android.gms.vision.Detector
//import com.google.android.gms.vision.barcode.Barcode
//import com.google.android.gms.vision.barcode.BarcodeDetector
//import com.google.android.gms.vision.Detector.Detections

import android.R as R2
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.CodeScannerView
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import net.fenki.qr.databinding.ActivityMainBinding


//import com.google.android.gms.vision.CameraSource
//import com.google.android.gms.vision.Detector
//import com.google.android.gms.vision.barcode.Barcode
//import com.google.android.gms.vision.barcode.BarcodeDetector
//import com.google.android.gms.vision.Detector.Detections

class MainActivity : AppCompatActivity(), GestureHandler.OnSwipeListener {
    private val requestCodeCameraPermission = 1001
//    private lateinit var cameraSource: CameraSource
//    private lateinit var barcodeDetector: BarcodeDetector
    public var scannedValue = ""
    private lateinit var binding: ActivityMainBinding



    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val button = findViewById<ImageView>(R.id.qr_code);
        val gesture = GestureHandler(this)
        button.setOnTouchListener(gesture)

        val button2 = findViewById<ImageView>(R.id.qr_button);
        button2.setOnClickListener { v:View ->
            scanCode()
        }
        var code = findViewById<TextView>(R.id.code_text)
        code?.text = Data.get()
//        if(scannedValue!="") code?.text = scannedValue
        if (ContextCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        }
    }

    @androidx.camera.core.ExperimentalGetImage
    private fun scanCode() {
        Data.set("")
        val intent = Intent(this, BrActivity::class.java)
//        intent.putExtra("key",1)
        startActivity(intent)

    }

    fun SlideLeft(){
        val qr = findViewById<ImageView>(R.id.qr_button);
        val slideIn = AnimationUtils.loadAnimation(this, R2.anim.slide_in_left)
        qr.setOnClickListener { v: View ->
            v.startAnimation(
                slideIn
            )
        }
    }
    fun SlideRight(){
        val qr = findViewById<ImageView>(R.id.qr_button);
        val slideIn = AnimationUtils.loadAnimation(this, R2.anim.slide_out_right)
        qr.setOnClickListener { v: View ->
            v.startAnimation(
                slideIn
            )
        }
    }
    fun onQRCodeClick(view: View){
        println("clicked QR Code")
        scanCode()
        val qr = findViewById<ImageView>(R.id.qr_code)
        performClickFeedback(view)
//        val anim = findViewById<ImageView>(R.id.button1);
//        performClickFeedback(qr)
//
//        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
//        qr.setOnClickListener { v: View ->
//            v.startAnimation(
//                slideIn
//            )
//        }
    }

     fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun onButtonClick(view: View){
//        showToast(message = "clicked on Button")
        val button = findViewById<ImageView>(R.id.qr_button);
        performClickFeedback(button)
        if (ContextCompat.checkSelfPermission(
                this@MainActivity, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }
    }
    private fun performClickFeedback(view: View) {
        // Provide visual and haptic feedback for the click
        view.isPressed = true
        view.postDelayed({ view.isPressed = false }, 100)
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

    }

fun setupControls(){

}

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        cameraSource.stop()
    }

    override fun onSwipeLeft() {
        showToast("Left swipe detected")
        SlideLeft()
    }

    override fun onSwipeRight() {
        showToast("Right swipe detected")
        SlideRight()
    }

    override fun getContext(): Context {
        return this
    }
}
