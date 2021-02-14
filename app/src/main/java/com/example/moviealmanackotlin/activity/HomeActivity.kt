package com.example.moviealmanackotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.adapters.TabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tabAdapter = TabAdapter(supportFragmentManager,lifecycle)
        view_pager_home.adapter = tabAdapter

        val tabTitles = arrayOf("Popular","Now Playing")
        TabLayoutMediator(tab_home,view_pager_home){tab,position ->
            tab.text =tabTitles[position]
        }.attach()
    }
}