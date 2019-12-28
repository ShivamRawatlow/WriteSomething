package com.thelegendofawizard.writesomething.ui.members

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thelegendofawizard.writesomething.PersonDetail
import com.thelegendofawizard.writesomething.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MembersViewModel(val application:Application, val repository: Repository) : ViewModel() {

    //private val allCloudMembers = MutableLiveData<List<PersonDetail>>()
    var alldatabasemembers:LiveData<List<PersonDetail>> = MutableLiveData<List<PersonDetail>>()
    var myEmail:String? = null
    fun getAllMembers() = repository.getAllMembers()

    init {
        repository.getFirebaseAuthInstance().currentUser.let {
            myEmail = it!!.email.toString()
        }
        databaseGetAllMembers()
    }


    fun databaseInsert(personDetail: PersonDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.databaseInsert(personDetail)
        }
    }

    fun databaseDelete(personDetail: PersonDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.databaseDelete(personDetail)
        }
    }

    fun databaseDeleteAllMembers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.databaseDeleteAllMembers()
        }
    }

    fun databaseGetAllMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            alldatabasemembers = repository.databaseGetAllMembers()
        }
    }
}