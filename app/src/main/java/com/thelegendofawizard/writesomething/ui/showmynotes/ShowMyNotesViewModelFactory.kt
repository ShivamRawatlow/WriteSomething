package com.thelegendofawizard.writesomething.ui.showmynotes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thelegendofawizard.writesomething.Repository

class  ShowMyNotesViewModelFactory(private val application: Application, private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowMyNotesViewModel(application, repository) as T
    }
}