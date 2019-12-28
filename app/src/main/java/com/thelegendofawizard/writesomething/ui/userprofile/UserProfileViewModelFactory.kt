package com.thelegendofawizard.writesomething.ui.userprofile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thelegendofawizard.writesomething.Repository

class UserProfileViewModelFactory(private val application: Application, private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserProfileViewModel(application, repository) as T
    }
}