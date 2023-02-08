package com.ikapurwanti.bwamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.ikapurwanti.bwamov.checkout.PilihBangkuActivity
import com.ikapurwanti.bwamov.home.dashboard.PlayAdapter
import com.ikapurwanti.bwamov.model.Film
import com.ikapurwanti.bwamov.model.Play
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mDatabase : DatabaseReference
    private var dataList =  ArrayList<Play>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // menggunakan val karena datanya tidak ada perubahan
        val data = intent.getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data!!.judul.toString())
            .child("play")

        tv_kursi.text = data.judul
        tv_genre.text = data.genre
        tv_desc.text = data.desc
        tv_rate.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster_image)

        // panggil datanya
        rv_who_play.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        btn_pilih_bangku.setOnClickListener{
            val intent = Intent(this, PilihBangkuActivity::class.java).putExtra("data", data)
            startActivity(intent)
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                dataList.clear()

                for (getdataSnapShot in p0.children){
                    var Film = getdataSnapShot.getValue(Play::class.java)
                    dataList.add(Film!!)

                }

                rv_who_play.adapter = PlayAdapter(dataList){

                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@DetailActivity, ""+p0.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}