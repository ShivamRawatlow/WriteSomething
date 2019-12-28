package com.thelegendofawizard.writesomething.ui.googlesignin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thelegendofawizard.writesomething.Repository

class GoogleSignInViewModelFactory(private val application: Application, private val repository: Repository)
    :ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GoogleSignInViewModel(application, repository) as T
    }

}