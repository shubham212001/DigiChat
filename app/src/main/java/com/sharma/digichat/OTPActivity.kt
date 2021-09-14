package com.sharma.digichat

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_o_t_p.*


class OTPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)
        val received =intent.getStringExtra("number_sending").toString()

        received_number.text="Verify "+received



        //Setting the spannable string
        val ss = SpannableString("Waiting to automatically detect a OTP sent to "+received+" Wrong Number ?")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
           //Here sending to the previous activity
                val intent1=Intent(this@OTPActivity,LoginActivity::class.java)
              startActivity(intent1)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color=ds.linkColor
            }
        }
        ss.setSpan(clickableSpan, 59, ss.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        waiting.movementMethod=LinkMovementMethod.getInstance()
        waiting.text=ss

    }

    override fun onBackPressed() {

    }



}