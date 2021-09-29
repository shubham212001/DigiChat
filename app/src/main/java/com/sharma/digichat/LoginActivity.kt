package com.sharma.digichat

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    lateinit var number:String
    lateinit var country:String

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

       name.addTextChangedListener{
           saver.isEnabled = !(it.isNullOrEmpty() || it.length < 10)

      }

        saver.setOnClickListener {
            reconfirm()
        }

    }

    private fun reconfirm() {
        country=code_picker.selectedCountryCodeWithPlus
        number =name.text.toString()
        var complete_number=country+number



            MaterialAlertDialogBuilder(this)
                    .setTitle("We will be veryfying the number" + complete_number + " \n")
                    .setMessage("Is this ok or would you like to edit ?")
                    .setNeutralButton("Edit") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setNegativeButton("Continue") { dialog, which ->
                        val intent = Intent(this, OTPActivity::class.java)
                        intent.putExtra("number_sending", complete_number)
                        startActivity(intent)
                        val dialog = ProgressDialog(this)
                        dialog.setMessage("Sending Verification Code..")
                        dialog.show()
                    }
                    .show()

    }
}