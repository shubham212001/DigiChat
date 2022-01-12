package com.sharma.digichat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_o_t_p.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.net.URI


class SignUp : AppCompatActivity() {
    private val pickImage = 100
    private var imageUri: Uri? = null
    val storage by lazy {
        FirebaseStorage.getInstance()
    }
    val auth by lazy {
        FirebaseAuth.getInstance()
    }
    val database by lazy {
        FirebaseFirestore.getInstance()
    }
    private lateinit var downloadUrl: String

    //@SuppressLint("ShowToast")
    fun OnbackPressed(){

    }
    override fun onCreate(savedInstanceState: Bundle?) {
//Creating the reference of storage ,auth and database


        supportActionBar?.hide()

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        name.addTextChangedListener {
//            save_me.isEnabled = !(it.isNullOrEmpty() || it.length < 1)
//        }
        set_image.setOnClickListener {
            checkImagePermission()
        }

        save_me.setOnClickListener {
            //A string may be empty but its not null

            val namer = name.text.toString()
            if (!::downloadUrl.isInitialized) {
                //toast("Photo cannot be empty")
                Toast.makeText(applicationContext,"Add a photo",Toast.LENGTH_SHORT).show()
            } else if (namer.isEmpty()) {
                //toast("Name cannot be empty")
                Toast.makeText(applicationContext,"Name cannot be empty",Toast.LENGTH_SHORT).show()
            } else {
                save_me.isEnabled=false
                //save_me.isEnabled=false
                val User = user(namer, downloadUrl, downloadUrl/*Needs to thumbnai url*/, auth.uid!!)
                database.collection("users").document(auth.uid!!).set(User).addOnSuccessListener {

                    val dialog = ProgressDialog(this)
                    dialog.setMessage("Uploading")
                    dialog.show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()



                }.addOnFailureListener {

                    save_me.isEnabled = true
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }



    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            1000
        ) // GIVE AN INTEGER VALUE FOR IMAGE_PICK_CODE LIKE 1000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            //let block will only be executed if the object being passed is not null
            data?.data?.let {
                set_image.setImageURI(it)
                startUpload(it)
            }
        }
    }


    private fun checkImagePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            ) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                val permissionWrite = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                requestPermissions(
                    permission,
                    1001
                ) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_READ LIKE 1001
                requestPermissions(
                    permissionWrite,
                    1002
                ) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_WRITE LIKE 1002
            } else {
                pickImageFromGallery()
            }
        }
    }
    private fun startUpload(filePath: Uri) {
        val ref = storage.reference.child("uploads/" + auth.uid.toString())
        val uploadTask = ref.putFile(filePath)
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUrl = task.result.toString()
                save_me.isEnabled = true
            } else {
                save_me.isEnabled = true
                // Handle failures
            }
        }.addOnFailureListener {
//This is what to do on failure
        }
    }
    }


