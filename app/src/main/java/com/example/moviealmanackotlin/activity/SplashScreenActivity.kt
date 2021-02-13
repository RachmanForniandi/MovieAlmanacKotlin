package com.example.moviealmanackotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviealmanackotlin.R
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar!!.hide()
        Timer("splashGone", true).schedule(3000) {
            startActivity<HomeActivity>()
            finish()
        }
    }
}