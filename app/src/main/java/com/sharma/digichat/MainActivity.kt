package com.sharma.digichat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //The main code for setting the pager adapter goes here
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = page_adapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }
}