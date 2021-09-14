package com.sharma.digichat

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_o_t_p.*


class OTPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val received =intent.getStringExtra("number_sending").toString()

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)


        received_number.text="Verify "+received

        //Below is the function to show the timer
        show_countdown_timer()
        //Below is the function to set the spannable string
        set_spannable_string(received)


    }

    private fun set_spannable_string(received:String) {
        //Setting the spannable string
        val ss = SpannableString("Waiting to automatically detect a OTP sent to " + received + " Wrong Number ?")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                //Here sending to the previous activity
                val intent1=Intent(this@OTPActivity, LoginActivity::class.java)
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

    private fun show_countdown_timer() {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter.setText("Resend OTP in: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
               resend_button.isEnabled=true
                counter.text=" "
            }
        }.start()
    }

    override fun onBackPressed() {

    }



}