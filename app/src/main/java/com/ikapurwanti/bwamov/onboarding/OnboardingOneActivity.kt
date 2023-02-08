package com.ikapurwanti.bwamov.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ikapurwanti.bwamov.R
import com.ikapurwanti.bwamov.sign.signin.SignInActivity
import com.ikapurwanti.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_onboarding_one.*

class OnboardingOneActivity : AppCompatActivity() {


    lateinit var preference : Preferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)


        preference = Preferences(this)
        if (preference.getValue("onboarding").equals("1")){
            finishAffinity()

            val intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
//
//        val btn_home = findViewById(R.id.btn_home) as Button
//        val btn_daftar = findViewById(R.id.btn_daftar) as Button

        btn_home.setOnClickListener(){
            val intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }

        btn_daftar.setOnClickListener(){
            preference.setValue("onboarding", "1")
            finishAffinity()
            // untuk menghapus page page sbelumnya

            val intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}