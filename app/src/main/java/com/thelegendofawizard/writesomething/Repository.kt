package com.thelegendofawizard.writesomething

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thelegendofawizard.writesomething.ui.googlesignin.FirebaseAuthSource
import com.thelegendofawizard.writesomething.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Repository (
    private val firebaseAuthSource: FirebaseAuthSource,
    private val firebaseDatabaseSource:FirebaseDatabaseSource,
    private val application: Application)
{
    val listProfilePics: MutableLiveData<MutableList<ProfilePic>> = MutableLiveData()

    val _listPersonDetail:MutableLiveData<MutableList<PersonDetail>> = MutableLiveData()

    val listPersonDetail: LiveData<MutableList<PersonDetail>>
    get() =_listPersonDetail


    companion object{
        val USER_NAME_KEY = "com.example.WriteSomething.UserNameKey"
        val USER_EMAIL_KEY = "com.example.WriteSomething.UserEmailkey"
        val MY_PREFERENCE_KEY ="com.example.WriteSomething.MyPreferenceKey"
    }
    
    init {

        getAllFirebaseMemebers()

        firebaseDatabaseSource.getProfilePics()
            .addSnapshotListener { snapshots, firestoreException ->
            if(firestoreException!=null){
                return@addSnapshotListener
            }
                val tempList:MutableList<ProfilePic> = mutableListOf()
            snapshots?.let {
                for(doc in snapshots){
                    val name = doc.getString("name")!!
                    val faceUrl = doc.getString("faceName")!!
                    val profilePic = ProfilePic(name,faceUrl)
                    tempList.add(profilePic)
                }
                listProfilePics.value =tempList
            }
        }
    }

    fun logout() = firebaseAuthSource.logout()

    fun getFirebaseAuthInstance() = firebaseAuthSource.getFirebaseAuthInstance()

    fun getAllNotes() = firebaseDatabaseSource.getAllNotes()

    fun getFirebaseDatabaseInstance() = firebaseDatabaseSource.getDataBaseInstance()

    fun getAllFirebaseMemebers(){

        firebaseDatabaseSource.getAllMembers()
            .addSnapshotListener { snapshots, firestoreException ->
                val tempPersonDetailList  = mutableListOf<PersonDetail>()
                if(firestoreException!=null){
                    application.applicationContext.toast(firestoreException.toString())
                    return@addSnapshotListener
                }
                for(doc in snapshots!!){
                    val name = doc.getString("name")!!
                    val email = doc.getString("email")!!
                    val about = doc.getString("about")!!
                    val url = doc.getString("url")!!
                    val personDetail = PersonDetail(email,name)
                    personDetail.about = about
                    personDetail.url = url
                    tempPersonDetailList.add(personDetail)
                }
                _listPersonDetail.value = tempPersonDetailList
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
                application.applicationContext.toast("Data updated successfully $type")

            else
                application.applicationContext.toast("Data update failed: ${it.exception}")
        }
    }

    fun getMemberByEmail(email: String):LiveData<PersonDetail>{

        val myPersonDetail: MutableLiveData<PersonDetail> = MutableLiveData()

        firebaseDatabaseSource.getMember(email).addSnapshotListener { snapshot, firestoreException ->
            val tempPersonDetailList  = mutableListOf<PersonDetail>()
            if(firestoreException!=null){
                application.applicationContext.toast(firestoreException.toString())
                return@addSnapshotListener
            }

            val name = snapshot!!.getString("name")!!
            val myEmail = snapshot.getString("email")!!
            val about = snapshot.getString("about")!!
            val url = snapshot.getString("url")!!
            val personDetail = PersonDetail(myEmail,name)
            personDetail.about = about
            personDetail.url = url
            myPersonDetail.value = personDetail
        }

        return myPersonDetail
    }

}