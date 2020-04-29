package com.example.myapplication

import android.os.Bundle
import androidx.camera.view.PreviewView
import com.example.myapplication.stuff.CameraActivity

class MainActivity : CameraActivity() {

    override lateinit var previewView: PreviewView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        previewView = findViewById(R.id.preview_view)

        launchCamera()
    }

}
