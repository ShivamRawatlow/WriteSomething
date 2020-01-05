package com.thelegendofawizard.writesomething

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members_table")
data class PersonDetail(

    @PrimaryKey(autoGenerate = false)
    val email:String,

    var name:String) {

    var about:String = ""

    var faceName:String = ""
}