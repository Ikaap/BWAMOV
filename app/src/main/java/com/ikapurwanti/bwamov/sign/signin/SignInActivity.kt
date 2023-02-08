package com.ikapurwanti.bwamov.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.ikapurwanti.bwamov.home.HomeActivity
import com.ikapurwanti.bwamov.R
import com.ikapurwanti.bwamov.sign.signup.SignUpActivity
import com.ikapurwanti.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    lateinit var iUsername:String
    lateinit var iPassword:String

    lateinit var mDatabase : DatabaseReference
    lateinit var preference : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preference = Preferences(this)

        preference.setValue("onboarding", "1") // onboarding tidak akan keluar lagi ketika sudah dinext, jadi hanya ditampilkan pada run aplikasi pertama
        if (preference.getValue("status").equals("1")){ // jadi ini statusnya 1 sudah login maka ketika di run akan langsung ke home
            finishAffinity()

            var goHome = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

//        val btn_home = findViewById(R.id.btn_home) as Button
//        val btn_daftar = findViewById(R.id.btn_daftar) as Button
//        val et_username = findViewById(R.id.et_username) as EditText
//        val et_password = findViewById(R.id.et_password) as EditText

        btn_home.setOnClickListener{
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            if (iUsername.equals("")){
                et_username.error = "Silakan tulis username Anda"
                et_username.requestFocus() // agar cursor fokus ke username
            }else if (iPassword.equals("")){
                et_password.error = "Silakan tulis password Anda"
                et_password.requestFocus() // agar cursor fokus ke username
            } else{
                pushLogin(iUsername, iPassword)
            }
        }

        btn_daftar.setOnClickListener{
            var goSignup = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(goSignup)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null){
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_LONG).show()
                }
                else {

                    if(user.password.equals(iPassword)){

                        preference.setValue("nama", user.nama.toString())
                        preference.setValue("user", user.username.toString())
                        preference.setValue("url", user.url.toString())
                        preference.setValue("email", user.email.toString())
                        preference.setValue("saldo", user.saldo.toString())
                        preference.setValue("status", "1") // status dari login, 1 = sudah login, 0 = belum login

                        var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity, "Password Anda salah", Toast.LENGTH_LONG).show()
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}