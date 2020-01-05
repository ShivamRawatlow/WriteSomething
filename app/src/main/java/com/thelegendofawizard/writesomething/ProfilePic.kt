package com.thelegendofawizard.writesomething

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "profile_pic_table")
data class ProfilePic(
    @PrimaryKey(autoGenerate = false)
    val faceName:String,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val faceData:ByteArray)

