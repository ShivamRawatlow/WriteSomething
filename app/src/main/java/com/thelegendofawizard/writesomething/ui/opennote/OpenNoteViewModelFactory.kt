package com.thelegendofawizard.writesomething.ui.opennote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thelegendofawizard.writesomething.Repository

class OpenNoteViewModelFactory(private val application: Application, private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OpenNoteViewModel(application, repository) as T
    }
}