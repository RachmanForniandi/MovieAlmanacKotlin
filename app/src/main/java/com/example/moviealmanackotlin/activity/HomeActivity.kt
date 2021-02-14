package com.example.moviealmanackotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.adapters.TabAdapter
import com.example.moviealmanackotlin.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_home)

        val tabAdapter = TabAdapter(supportFragmentManager,lifecycle)
        binding.viewPagerHome.adapter = tabAdapter

        val tabTitles = arrayOf("Popular","Now Playing")
        TabLayoutMediator(binding.tabHome,binding.viewPagerHome){tab,position ->
            tab.text =tabTitles[position]
        }.attach()
    }
}