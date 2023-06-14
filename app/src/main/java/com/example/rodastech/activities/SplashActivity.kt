package com.example.rodastech.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.rodastech.R


class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(

            {
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
            , SPLASH_TIME_OUT)
    }
}