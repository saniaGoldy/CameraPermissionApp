package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.stuff.CameraActivity

const val MY_PERMISSION_CODE = 100

class MainActivity : CameraActivity() {

    override lateinit var previewView: PreviewView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        previewView = findViewById(R.id.preview_view)
        if (havePermissionToCamera()
        ) {
            Log.d("PERMISSION", "CAMERA PERMISSION IS NOT GRANTED")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                showAlertDialog()
            }
            Log.d("PERMISSION", "REQUEST CAMERA PERMISSION")
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                MY_PERMISSION_CODE
            )
            if(havePermissionToCamera())
                launchCamera()
        }else{
            launchCamera()
        }
    }

    private fun havePermissionToCamera() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_DENIED
}
