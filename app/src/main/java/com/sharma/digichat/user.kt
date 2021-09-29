package com.sharma.digichat

data class user (  val name: String,
                   val imageUrl: String,
                   val thumbImage: String,
                   val deviceToken: String,
                   val status: String,
                   val online: Boolean,
                   val uid: String){

    constructor() : this("", "", "", "", "Hey There, I am using DigiChat", false, "")

    constructor(name: String, imageUrl: String, thumbImage: String, uid: String) :
            this(name, imageUrl, thumbImage, "", uid = uid, status = "Hey There, I am using DigiChat", online = false)
}