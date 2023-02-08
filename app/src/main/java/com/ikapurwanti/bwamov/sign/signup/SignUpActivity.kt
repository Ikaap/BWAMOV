package com.ikapurwanti.bwamov.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import com.ikapurwanti.bwamov.R
import com.ikapurwanti.bwamov.sign.signin.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername : String
    lateinit var sPassword : String
    lateinit var sNama : String
    lateinit var sEmail : String

    lateinit var mDatabaseReference: DatabaseReference
    lateinit var mFirebaseIntance : FirebaseDatabase
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseIntance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseIntance.getReference("User")

//        val btn_lanjutkan = findViewById(R.id.btn_lanjutkan) as Button
//        val et_username = findViewById(R.id.et_username) as EditText
//        val et_password = findViewById(R.id.et_password) as EditText
//        val et_nama = findViewById(R.id.et_nama) as EditText
//        val et_email = findViewById(R.id.et_email) as EditText

        btn_lanjutkan.setOnClickListener{
            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sNama = et_nama.text.toString()
            sEmail = et_email.text.toString()

            if (sUsername.equals("")){
                et_username.error = "Silakan isi username Anda"
                et_username.requestFocus()
            } else if (sPassword.equals("")){
                et_password.error = "Silakan isi password Anda"
                et_password.requestFocus()
            }else if (sNama.equals("")){
                et_nama.error = "Silakan isi nama Anda"
                et_nama.requestFocus()
            }else if (sEmail.equals("")){
                et_email.error = "Silakan isi email Anda"
                et_email.requestFocus()
            } else {
                saveUsername (sUsername, sPassword, sNama, sEmail)
            }
        }
    }

    private fun saveUsername(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        var user = User()
        user.username = sUsername
        user.password = sPassword
        user.nama = sNama
        user.email = sEmail

        if (sUsername != null){
            checingUsername(sUsername, user)
        }
    }

    private fun checingUsername(sUsername: String, data: User) {
        mDatabaseReference.child(sUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if (user == null){
                    mDatabaseReference.child(sUsername).setValue(data)

                    var goSignupPhotoScreen = Intent(this@SignUpActivity, SignUpPhotoscreenActivity::class.java).putExtra("nama", data.nama) // berpindah ke activity lain dengan membawa data
                    startActivity(goSignupPhotoScreen)

                } else {
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}