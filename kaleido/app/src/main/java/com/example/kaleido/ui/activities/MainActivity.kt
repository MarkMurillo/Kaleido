package com.example.kaleido.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kaleido.R

class MainActivity : AppCompatActivity() {
    // Shared flag between fragments
    var hideUI = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}