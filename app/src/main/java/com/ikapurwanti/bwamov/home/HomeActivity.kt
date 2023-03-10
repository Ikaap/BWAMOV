package com.ikapurwanti.bwamov.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.ikapurwanti.bwamov.R
import com.ikapurwanti.bwamov.home.dashboard.DashboardFragment
import com.ikapurwanti.bwamov.home.setting.SettingFragment
import com.ikapurwanti.bwamov.home.tiket.TiketFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    //var layout_frame = findViewById(R.id.layout_frame) as FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentTiket = TiketFragment()
        val fragmentSetting = SettingFragment()
        val fragmentHome = DashboardFragment()

        setFragment(fragmentHome)

//        val iv_menu1 = findViewById(R.id.iv_menu1) as ImageView
//        val iv_menu2 = findViewById(R.id.iv_menu2) as ImageView
//        val iv_menu3 = findViewById(R.id.iv_menu3) as ImageView

        iv_menu1.setOnClickListener {
            setFragment(fragmentHome)

            changeIcon(iv_menu1, R.drawable.ic_home_active)
            changeIcon(iv_menu2, R.drawable.ic_tiket)
            changeIcon(iv_menu3, R.drawable.ic_profile)
        }

        iv_menu2.setOnClickListener {
            setFragment(fragmentTiket)

            changeIcon(iv_menu1, R.drawable.ic_home)
            changeIcon(iv_menu2, R.drawable.ic_tiket_active)
            changeIcon(iv_menu3, R.drawable.ic_profile)
        }

        iv_menu3.setOnClickListener {
            setFragment(fragmentSetting)

            changeIcon(iv_menu1, R.drawable.ic_home)
            changeIcon(iv_menu2, R.drawable.ic_tiket)
            changeIcon(iv_menu3, R.drawable.ic_profile_active)
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }
}

