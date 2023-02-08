package com.ikapurwanti.bwamov.sign.signup

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ikapurwanti.bwamov.home.HomeActivity
import com.ikapurwanti.bwamov.R
import com.ikapurwanti.bwamov.utils.Preferences
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_sign_up_photoscreen.*
import java.util.*

class SignUpPhotoscreenActivity : AppCompatActivity(), PermissionListener{

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    lateinit var filePath: Uri

    lateinit var storage : FirebaseStorage
    lateinit var storageReferensi : StorageReference
    lateinit var preferences : Preferences

//    val tv_hello = findViewById(R.id.tv_hello) as TextView
//    val iv_add = findViewById(R.id.iv_add) as ImageView
//    val iv_profile = findViewById(R.id.iv_profile) as ImageView
//    val btn_save = findViewById(R.id.btn_save) as Button
//    val btn_home = findViewById(R.id.btn_home) as Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()

        tv_hello.text = "Selamat Datang\n"+intent.getStringArrayExtra("nama")

        iv_add.setOnClickListener{
            if (statusAdd) {
                statusAdd = false
                btn_save.visibility = View.VISIBLE
                iv_add.setImageResource(R.drawable.ic_btn_upload)
                iv_profile.setImageResource(R.drawable.user_pic)
            } else {
                Dexter.withActivity(this)
                    .withPermission(android.Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()



            }
        }

        btn_home.setOnClickListener{
            finishAffinity()

            val goHome = Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        btn_save.setOnClickListener{
            if (filePath != null){
                // membuat proggres dialog
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                // dengan folder yang ada di firebasenya
                var ref = storageReferensi.child("images/"+ UUID.randomUUID().toString())
                ref.putFile(filePath) // kasih filenya dengan uri tadi / filepath
                    .addOnSuccessListener {
                        // jika sukses matikan progress dialognya
                        progressDialog.dismiss()
                        Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show()

                        // untuk url nya di save ke share preferences
                        ref.downloadUrl.addOnSuccessListener {
                            preferences.setValue("url", it.toString())
                        }

                        finishAffinity()
                        // kalo sukses pindah ke home
                        var goHome = Intent(this, HomeActivity::class.java)
                        startActivity(goHome)
                    }

                    // jika tidak sukses
                    .addOnFailureListener{
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                    }

                    // untuk menampilkan berapa persen yang sudah terupload
                    .addOnProgressListener {
                        taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload "+progress.toInt()+" %")
                    }

            } else {


            }
        }

    }

    // kalau disetujui
    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
            takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Anda tidak bisa menambahkan photo profile", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah? Klik tombol upload nanti aja", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            filePath = data.getData()!!
            // munculin foto
            Glide.with(this)
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform()) // menjadikan gambarnya lingkaran
                .into(iv_profile) // memasukannya ke view


            btn_save.visibility = View.VISIBLE
            iv_add.setImageResource(R.drawable.ic_btn_delete)
        }
    }
}