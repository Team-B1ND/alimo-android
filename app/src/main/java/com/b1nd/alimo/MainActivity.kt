package com.b1nd.alimo

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.b1nd.alimo.feature.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FrameLayout>(R.id.frameLayout)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, ProfileFragment())
            .commit()
    }
}