package com.thelegendofawizard.writesomething

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thelegendofawizard.writesomething.roomdatabase.LocalDatabase
import com.thelegendofawizard.writesomething.roomdatabase.LocalDatabaseDao
import com.thelegendofawizard.writesomething.roomdatabase.PicsDatabaseDao
import com.thelegendofawizard.writesomething.ui.googlesignin.FirebaseAuthSource
import com.thelegendofawizard.writesomething.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Repository (
    private val firebaseAuthSource: FirebaseAuthSource,
    private val firebaseDatabaseSource:FirebaseDatabaseSource,
    private val firebaseStorageSource: FirebaseStorageSource,
    private val database : LocalDatabase,
    private val application: Application)
{
    var ListProfilePics: LiveData<List<ProfilePic>> = MutableLiveData<List<ProfilePic>>()

    init {
        storeMembersLocalDatabase()
        //storePicsLocalDatabase()
        GlobalScope.launch(Dispatchers.IO) {
            ListProfilePics = databaseGetProfilePics()
        }
    }

    fun storePicsLocalDatabase(){
        for (i in 1..7) {
            val MAX_SIZE = (800 * 600).toLong()
            firebaseStorageSource.getMyStorageRef().getReference("ProfilePics/face$i.png")
                .getBytes(MAX_SIZE)
                    .addOnCompleteListener {

                    if (!it.isSuccessful) {
                        application.toast("not face$i: ${it.exception}")
                        return@addOnCompleteListener
                    }
                    val byteArray: ByteArray = it.result!!
                    val profilePic = ProfilePic("face$i",byteArray)
                        application.toast("face$i")
                    GlobalScope.launch(Dispatchers.IO) {
                        databaseInsertProfilePics(profilePic)
                    }

                }
        }
    }


    /*fun getProfilePics(){
        firebaseDatabaseSource.getProfilePics().addSnapshotListener{snapshot, firestoreException ->
            if(firestoreException!=null){
                return@addSnapshotListener
            }
            for(i in 1..10){
                val tempProfileFace = ProfilePic("face$i", snapshot?.getString("face$i"))
                ListProfilePics.value?.add(tempProfileFace)
            }

        }

    }*/



    fun logout() = firebaseAuthSource.logout()

    fun currentUser() = firebaseAuthSource.currentUser()

    fun getFirebaseAuthInstance() = firebaseAuthSource.getFirebaseAuthInstance()

    fun getAllNotes() = firebaseDatabaseSource.getAllNotes()

    fun getFirebaseDatabaseInstance() = firebaseDatabaseSource.getDataBaseInstance()

    fun storeMembersLocalDatabase() {
        firebaseDatabaseSource.getAllMembers()
            .addSnapshotListener { snapshots, firestoreException ->
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
    private val picsDatabaseDao:PicsDatabaseDao = database.picsDatabaseDao()

    suspend fun databaseInsert(personDetail: PersonDetail) = localDatabaseDao.insert(personDetail)

    suspend fun databaseDelete(personDetail: PersonDetail) = localDatabaseDao.delete(personDetail)

    suspend fun databaseDeleteAllMembers() = localDatabaseDao.deleteAllMembers()

    suspend fun databaseGetAllMembers() = localDatabaseDao.getAllMembers()

    suspend fun databaseGetMemberByEmail(email:String) = localDatabaseDao.getMemberByEmail(email)

    suspend fun databaseInsertProfilePics(pic: ProfilePic) = picsDatabaseDao.insert(pic)

    suspend fun databaseGetProfilePics() = picsDatabaseDao.getAllPics()

    suspend fun databaseGetPicByFaceNamec(faceName:String) = picsDatabaseDao.getPicByFaceName(faceName)

}