package com.thelegendofawizard.writesomething.ui.deleteuser

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.google.android.material.internal.ContextUtils.getActivity
import com.thelegendofawizard.writesomething.MainActivity
import com.thelegendofawizard.writesomething.Repository
import com.thelegendofawizard.writesomething.ui.googlesignin.GoogleSignInActivity
import com.thelegendofawizard.writesomething.utils.toast
import kotlin.system.exitProcess

class DeleteUserViewModel(val application: Application, val repository: Repository) : ViewModel() {
    // TODO: Implement the ViewModel

    fun deleteUser(){
        repository.getFirebaseAuthInstance().currentUser?.let {
            it.delete().addOnSuccessListener {
                application.applicationContext.toast("UserDeleted!!")

            }
                .addOnFailureListener {
                    application.applicationContext.toast(it.toString())
            }
        }
    }
}
