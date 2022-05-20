package com.example.myapplication.stuff

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.google.common.util.concurrent.ListenableFuture

abstract class CameraActivity : AppCompatActivity() {

    abstract var previewView: PreviewView
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
    }

    protected fun launchCamera() {
        Log.d("MyApp", "Launching camera")
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun openSettings() {
        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + BuildConfig.APPLICATION_ID)
            )
        )
    }

    protected fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.alertTitle)
            .setMessage(R.string.alertMessage)
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                openSettings()
            }
            .setNegativeButton(android.R.string.no) { dialog, which ->

            }
            .show()
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)

        preview.setSurfaceProvider(previewView.createSurfaceProvider(camera.cameraInfo))
    }

}