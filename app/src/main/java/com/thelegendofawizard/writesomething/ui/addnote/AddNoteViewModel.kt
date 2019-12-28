package com.thelegendofawizard.writesomething.ui.addnote

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thelegendofawizard.writesomething.MyNote
import com.thelegendofawizard.writesomething.PersonDetail
import com.thelegendofawizard.writesomething.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel (val application: Application, val repository: Repository): ViewModel() {

    val myEmail = repository.getFirebaseAuthInstance().currentUser?.email.toString()
    var name = String()
    var body = MutableLiveData<String>()
    var title = MutableLiveData<String>()
    var tempPersonDetail: LiveData<PersonDetail> = MutableLiveData<PersonDetail>()

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
