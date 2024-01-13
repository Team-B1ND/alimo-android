package com.b1nd.alimo

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.b1nd.alimo.feature.test.TestFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FrameLayout>(R.id.frameLayout)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, TestFragment())
            .commit()
    }
}