package com.thelegendofawizard.writesomething


import com.google.firebase.Timestamp

data class MyNote(
    val id:String,
    val email:String,
    var name:String,
    var title:String?,
    var body:String?,
    var visibility:Boolean = true
)
