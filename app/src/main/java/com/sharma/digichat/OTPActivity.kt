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
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_o_t_p.*
import java.util.concurrent.TimeUnit


class OTPActivity : AppCompatActivity() {
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var phoneNumber: String? = null
    private var mVerificationId: String? = null
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        val received = intent.getStringExtra("number_sending").toString()
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)
        received_number.text = "Verify " + received

        //Below is the function to show the timer
        show_countdown_timer()
        //Below is the function to set the spannable string
        set_spannable_string(received)
        //Start Verification
        initialiseView()
        startVerify(received)

    }

    private fun initialiseView() {
        //Creating and calling the callbacks /Methods concerned with the working of the phone authorisation
        //using Firebase
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {


                val smsMessageSent = credential.smsCode
                if (!smsMessageSent.isNullOrBlank())
                    OTP_input.setText(smsMessageSent)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.


                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.


                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                mResendToken = token
            }
        }
    }

    private fun startVerify(phone_number: String) {
//Now we are calling the functions to make a send the code to the registered phone number

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone_number,      // Phone number to verify
                60,               // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                this,            // Activity (for callback binding)
                callbacks
        ) // OnVerificationStateChangedCallbacks


    }

    private fun set_spannable_string(received: String) {
        //Setting the spannable string
        val ss = SpannableString("Waiting to automatically detect a OTP sent to " + received + " Wrong Number ?")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                //Here sending to the previous activity
                val intent1 = Intent(this@OTPActivity, LoginActivity::class.java)
                startActivity(intent1)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = ds.linkColor
            }
        }
        ss.setSpan(clickableSpan, 59, ss.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        waiting.movementMethod = LinkMovementMethod.getInstance()
        waiting.text = ss
    }

    private fun show_countdown_timer() {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter.setText("Resend OTP in: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                resend_button.isEnabled = true
                counter.text = " "
            }
        }.start()
    }

    override fun onBackPressed() {

    }


}