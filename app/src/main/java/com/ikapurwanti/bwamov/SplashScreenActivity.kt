package com.ikapurwanti.bwamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ikapurwanti.bwamov.onboarding.OnboardingOneActivity
/*
    ini adalah activity pertama yang akan di run
    tidak ada fitur spesial di sini
    hanya melakukan pending beberapa detik saja
 */

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity, OnboardingOneActivity::class.java)
            startActivity(intent)
            // mendestroy kalo user mau back dari onboarding one tidak akan balik ke halaman ini
            finish()

        },5000)
    }
}