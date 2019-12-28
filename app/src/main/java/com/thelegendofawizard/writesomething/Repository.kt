package com.thelegendofawizard.writesomething

import android.app.Application
import com.thelegendofawizard.writesomething.ui.googlesignin.FirebaseAuthSource
import com.thelegendofawizard.writesomething.roomdatabase.LocalDatabase
import com.thelegendofawizard.writesomething.roomdatabase.LocalDatabaseDao
import com.thelegendofawizard.writesomething.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Repository (
    private val firebaseAuthSource: FirebaseAuthSource,
    private val firebaseDatabaseSource:FirebaseDatabaseSource,
    private val database : LocalDatabase,
    private val application: Application){

    init {

        getAllMembers().addSnapshotListener { snapshots, firestoreException ->
            val tempPersonDetailList  = mutableListOf<PersonDetail>()
            if(firestoreException!=null){
                return@addSnapshotListener
            }
            snapshots?.let {
                for(doc in snapshots){

                    val name = doc.getString("name")!!
                    val email = doc.getString("email")!!
                    val about = doc.getString("about")!!
                    val personDetail = PersonDetail(email,name)
                    personDetail.about = about
                    tempPersonDetailList.add(personDetail)
                }
            }

            for (members in tempPersonDetailList){
                GlobalScope.launch(Dispatchers.IO) {
                    databaseInsert(members)
                }
            }
        }

    }


    fun logout() = firebaseAuthSource.logout()

    fun currentUser() = firebaseAuthSource.currentUser()

    fun getFirebaseAuthInstance() = firebaseAuthSource.getFirebaseAuthInstance()

    fun getAllNotes() = firebaseDatabaseSource.getAllNotes()


    fun getAllMembers() = firebaseDatabaseSource.getAllMembers()

    fun getNotesOfMember(email: String) = firebaseDatabaseSource.getNotesOfMember(email)

    fun saveUser(personDetail: PersonDetail) {
        firebaseDatabaseSource.saveUser(personDetail).addOnCompleteListener {
            if(it.isSuccessful)
                application.applicationContext.toast("User saved successfully")

            else
                application.applicationContext.toast("User saving failed: ${it.exception}")
        }
    }


    fun deleteNote(myNote: MyNote){
        firebaseDatabaseSource.deleteNote(myNote).addOnCompleteListener {
            if(it.isSuccessful)
                application.applicationContext.toast("Note deleted successfully")

            else
                application.applicationContext.toast("Note deletion failed: ${it.exception}")
        }
    }


    fun saveNote(myNote:MyNote) {
        firebaseDatabaseSource.saveNote(myNote).addOnCompleteListener {
            if(it.isSuccessful)
                application.applicationContext.toast("Note saved  successfully")

            else
                application.applicationContext.toast("Note saving failed: ${it.exception}")
        }
    }



    fun  updateMemberDetail(email:String,type:String, value:String){

        firebaseDatabaseSource.updateMemberDetail(email,type,value).addOnCompleteListener {
            if(it.isSuccessful)
                application.applicationContext.toast("Data updated successfully")

            else
                application.applicationContext.toast("Data update failed: ${it.exception}")
        }
    }

    fun getMember(email: String) = firebaseDatabaseSource.getMember(email)


    private val localDatabaseDao: LocalDatabaseDao = database.localDatabaseDao()

    suspend fun databaseInsert(personDetail: PersonDetail) = localDatabaseDao.insert(personDetail)

    suspend fun databaseDelete(personDetail: PersonDetail) = localDatabaseDao.delete(personDetail)

    suspend fun databaseDeleteAllMembers() = localDatabaseDao.deleteAllMembers()

    suspend fun databaseGetAllMembers() = localDatabaseDao.getAllMembers()

    suspend fun databaseGetMemberByEmail(email:String) = localDatabaseDao.getMemberByEmail(email)

}