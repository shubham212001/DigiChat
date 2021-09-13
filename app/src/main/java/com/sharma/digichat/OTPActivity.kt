package com.sharma.digichat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_o_t_p.*

class OTPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)
        val received =intent.getStringExtra("number_sending").toString()

        received_number.text="Verify "+received


    }
}