package com.thelegendofawizard.writesomething

import java.util.*

data class MyNote(val id:String,
                  val email:String,
                  var name:String,
                  var title:String?,
                  var body:String?,
                  var visibility:Boolean = true
                )
