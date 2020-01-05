package com.thelegendofawizard.writesomething.ui.googlesignin

import android.app.Application
import androidx.lifecycle.ViewModel
import com.thelegendofawizard.writesomething.Repository
import com.thelegendofawizard.writesomething.PersonDetail

class GoogleSignInViewModel (private val application: Application,private val repository: Repository) : ViewModel(){


    init {
        repository.storePicsLocalDatabase()
    }

     fun getFirebaseAuthInstance() = repository.getFirebaseAuthInstance()

    fun getCurrentUser() = repository.currentUser()

    fun saveUser(personDetail: PersonDetail) = repository.saveUser(personDetail)
}