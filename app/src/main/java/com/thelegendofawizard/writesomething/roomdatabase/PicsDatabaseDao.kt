package com.thelegendofawizard.writesomething.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thelegendofawizard.writesomething.PersonDetail
import com.thelegendofawizard.writesomething.ProfilePic

@Dao
interface PicsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profilePic: ProfilePic)

    @Delete
    fun delete(profilePic: ProfilePic)

    @Query("DELETE  FROM profile_pic_table")
    fun deleteAllPics()

    @Query("SELECT * FROM profile_pic_table")
    fun getAllPics() : LiveData<List<ProfilePic>>

    @Query("SELECT faceData FROM profile_pic_table WHERE faceName LIKE :faceName")
    fun getPicByFaceName(faceName:String):LiveData<ByteArray>

}