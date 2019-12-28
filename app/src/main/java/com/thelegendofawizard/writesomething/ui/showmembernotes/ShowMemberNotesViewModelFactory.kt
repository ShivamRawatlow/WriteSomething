package com.thelegendofawizard.writesomething.ui.showmembernotes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thelegendofawizard.writesomething.Repository
import com.thelegendofawizard.writesomething.ui.showmynotes.ShowMyNotesViewModel

class ShowMemberNotesViewModelFactory(private val application: Application, private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowMemberNotesViewModel(application, repository) as T
    }
}