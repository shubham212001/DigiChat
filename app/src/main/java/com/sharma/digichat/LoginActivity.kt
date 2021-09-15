package com.sharma.digichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.accept
import android.widget.Toast
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
       phone_number.addTextChangedListener{
      if(it!!.length==10){
          send_button.isEnabled
      }

      }

        send_button.setOnClickListener {
            reconfirm()
        }

    }

    private fun reconfirm() {
        country=code_picker.selectedCountryCodeWithPlus
        number =phone_number.text.toString()
        var complete_number=country+number



            MaterialAlertDialogBuilder(this)
                    .setTitle("We will be veryfying the number" + complete_number + " \n")
                    .setMessage("Is this ok or would you like to edit")
                    .setNeutralButton("Edit") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setNegativeButton("Continue") { dialog, which ->
                        val intent = Intent(this, OTPActivity::class.java)
                        intent.putExtra("number_sending", complete_number)
                        startActivity(intent)
                    }
                    .show()

    }
}