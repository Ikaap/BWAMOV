package com.ikapurwanti.bwamov.home.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.ikapurwanti.bwamov.DetailActivity
import com.ikapurwanti.bwamov.R
import com.ikapurwanti.bwamov.model.Film
import com.ikapurwanti.bwamov.utils.Preferences
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.fragment_dashboard.*

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences // data sementara
    private lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_nama.setText(preferences.getValue("nama"))
        if (!preferences.getValue("saldo").equals("")){
            currency(preferences.getValue("saldo")!!.toDouble(), tv_saldo)
        }

        Glide.with(this)
            .load(preferences.getValue("url"))
            .apply(RequestOptions.circleCropTransform()) // menjadikan gambarnya lingkaran
            .into(iv_profile) // saat ditampilkan akan ditampilkan pada view ini


        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coming_soon.layoutManager = LinearLayoutManager(context!!.applicationContext) // karna vertical jadi cukup samoai context
        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear() // data list nya dihapus dulu agar tidak terjadi duplikat
                for (getDataSnapShot in dataSnapshot.children){
                    // variabel untuk menampung data jika ada
                    var film = getDataSnapShot.getValue(Film::class.java)
                    dataList.add(film!!) // datanya dimasukin juga ke dataList
                }

                // atur adapter
                rv_now_playing.adapter = NowPlayingAdapter(dataList){
                    // mengiri data
                    var intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

                rv_coming_soon.adapter = ComingSoonAdapter(dataList){
                    var intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    // fungsi untuk format uang
    private fun currency(harga : Double, textView : TextView){
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.setText(format.format(harga))

    }

}