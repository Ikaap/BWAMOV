package com.ikapurwanti.bwamov.home.tiket

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.ikapurwanti.bwamov.R
import com.ikapurwanti.bwamov.home.dashboard.ComingSoonAdapter
import com.ikapurwanti.bwamov.model.Film
import com.ikapurwanti.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_pilih_bangku.view.*
import kotlinx.android.synthetic.main.fragment_tiket.*

/**
 * A simple [Fragment] subclass.
 * Use the [TiketFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TiketFragment : Fragment() {

    private lateinit var preferences : Preferences // database sementara
    private lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tiket, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        rv_tiket.layoutManager = LinearLayoutManager(context)
        gatData()

    }

    private fun gatData() {
        // ambil data yang ada di firebase
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (getDataSnapShot in  snapshot.children){
                    val film = getDataSnapShot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                // set adapter
                rv_tiket.adapter = ComingSoonAdapter(dataList) {
                    val intent = Intent(context, TiketActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

                tv_total.setText("${dataList.size} Movies")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

}