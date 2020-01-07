package com.thelegendofawizard.writesomething.ui.addnote

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.thelegendofawizard.writesomething.MyNote
import com.thelegendofawizard.writesomething.PersonDetail
import com.thelegendofawizard.writesomething.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel (val application: Application, val repository: Repository): ViewModel() {

   val myEmail = repository.getFirebaseAuthInstance().currentUser?.email.toString()
    //val myEmail = FirebaseAuth.getInstance().currentUser?.email.toString()

    //val pref: SharedPreferences = application.getSharedPreferences(Repository.MY_PREFERENCE_KEY, Context.MODE_PRIVATE)
    //val myEmail = pref.getString(Repository.USER_EMAIL_KEY,"")


    val name = MutableLiveData<String>()
    var body = MutableLiveData<String>()
    var title = MutableLiveData<String>()
    var tempPersonDetail: LiveData<PersonDetail> = MutableLiveData<PersonDetail>()
    var firebaseInstance = repository.getFirebaseDatabaseInstance()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tempPersonDetail = repository.databaseGetMemberByEmail(myEmail)
        }

    }

    fun saveNote(note: MyNote){
        repository.saveNote(note)
    }

    fun deleteNote(note:MyNote){
        repository.deleteNote(note)
    }

    fun updateNote(note:MyNote){
        repository.saveNote(note)
    }
}
