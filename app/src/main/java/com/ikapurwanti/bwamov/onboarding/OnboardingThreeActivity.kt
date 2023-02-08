package com.ikapurwanti.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ikapurwanti.bwamov.R
import com.ikapurwanti.bwamov.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding_three.*

class OnboardingThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

//        val btn_home = findViewById(R.id.btn_home) as Button

        btn_home.setOnClickListener(){
            finishAffinity()
            // untuk menghapus page page sbelumnya

            val intent = Intent(this@OnboardingThreeActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}