package com.thelegendofawizard.writesomething.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thelegendofawizard.writesomething.PersonDetail

@Dao
interface LocalDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(personDetail: PersonDetail)

    @Delete
    fun delete(personDetail: PersonDetail)

    @Query("DELETE  FROM members_table")
    fun deleteAllMembers()

    @Query("SELECT * FROM members_table ORDER BY name DESC")
    fun getAllMembers() : LiveData<List<PersonDetail>>

    @Query("SELECT * FROM members_table WHERE email LIKE :email")
    fun getMemberByEmail(email:String):LiveData<PersonDetail>

}