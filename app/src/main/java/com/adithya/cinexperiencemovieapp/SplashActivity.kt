package com.adithya.cinexperiencemovieapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private val handler = Handler(Looper.getMainLooper())
    private var progressStatus = 0
    private val totalDuration = 3000L  // 3 seconds
    private val updateInterval = 50L   // update every 50 milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.progressBar)
        progressBar.max = 100
        progressBar.progress = 0

        startProgress()
    }

    private fun startProgress() {
        val increment = 100 * updateInterval / totalDuration

        handler.post(object : Runnable {
            override fun run() {
                progressStatus += increment.toInt()
                if (progressStatus <= 100) {
                    progressBar.progress = progressStatus
                    handler.postDelayed(this, updateInterval)
                } else {
                    // Progress complete, navigate to MainActivity
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }
}
