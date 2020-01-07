package com.thelegendofawizard.writesomething.roomdatabase


import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thelegendofawizard.writesomething.PersonDetail
import com.thelegendofawizard.writesomething.ProfilePic


@Database(entities = [PersonDetail::class],version = 1,exportSchema = false)
abstract class LocalDatabase:RoomDatabase()
{

    abstract fun localDatabaseDao(): LocalDatabaseDao
    //abstract fun picsDatabaseDao():PicsDatabaseDao

    companion object {
        @Volatile private var instance: LocalDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                LocalDatabase::class.java, "WriteSomethingDatabase.db")
        .build()
    }
}