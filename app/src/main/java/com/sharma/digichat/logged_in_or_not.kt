package com.sharma.digichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class logged_in_or_not : AppCompatActivity() {
    val auth by lazy {
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in_or_not)
        if(auth.currentUser == null){
            startActivity(Intent(this,LoginActivity::class.java))
        }else{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}