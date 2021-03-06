package com.thelegendofawizard.writesomething.ui.myprofile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.thelegendofawizard.writesomething.PersonDetail
import com.thelegendofawizard.writesomething.ProfilePic
import com.thelegendofawizard.writesomething.Repository
import com.thelegendofawizard.writesomething.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


import kotlinx.coroutines.launch

class MyProfileViewModel(val application: Application, val repository: Repository):ViewModel() {

    val myEmail:String? = repository.getFirebaseAuthInstance().currentUser?.email.toString()

        var name = MutableLiveData<String>()
        var email = MutableLiveData<String>()
        var about = MutableLiveData<String>()

        var url = MutableLiveData<String>()

        var tempPersonDetail:LiveData<PersonDetail> = MutableLiveData<PersonDetail>()

    init {
           // application.applicationContext.toast(myEmail!!)

            viewModelScope.launch(Dispatchers.IO){
                tempPersonDetail = repository.getMemberByEmail(myEmail!!)

        }
    }

    fun onButtonClick(view: View) {
            val action = MyProfileFragmentDirections.actionMyProfileFragmentToShowMyNotesFragment()
            view.findNavController().navigate(action)
    }
}