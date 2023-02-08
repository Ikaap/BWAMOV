package com.ikapurwanti.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ikapurwanti.bwamov.R
import com.ikapurwanti.bwamov.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding_two.*

class OnboardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)
//
//        val btn_home = findViewById(R.id.btn_home) as Button
//        val btn_daftar = findViewById(R.id.btn_daftar) as Button

        btn_home.setOnClickListener(){
            val intent = Intent(this@OnboardingTwoActivity, OnboardingThreeActivity::class.java)
            startActivity(intent)
        }

        btn_daftar.setOnClickListener(){
            finishAffinity()

            val intent = Intent(this@OnboardingTwoActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}