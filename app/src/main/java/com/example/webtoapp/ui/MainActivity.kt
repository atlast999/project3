package com.example.webtoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.webtoapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.app_name)
    }
}