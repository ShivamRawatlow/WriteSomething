package com.thelegendofawizard.writesomething.ui.myprofile

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.thelegendofawizard.writesomething.PersonDetail
import com.thelegendofawizard.writesomething.ProfilePic
import com.thelegendofawizard.writesomething.Repository
import com.thelegendofawizard.writesomething.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyProfileViewModel(val application: Application, val repository: Repository):ViewModel() {

    val myEmail = repository.currentUser()?.email.toString()
    var name = MutableLiveData<String>()
    var profilePic:LiveData<ProfilePic> = MutableLiveData<ProfilePic>()

        var email = MutableLiveData<String>()
        var about = MutableLiveData<String>()

        var faceName = MutableLiveData<String>()
        var faceData:LiveData<ByteArray> = MutableLiveData<ByteArray>()

        var tempPersonDetail:LiveData<PersonDetail> = MutableLiveData<PersonDetail>()

    init {

        if(!myEmail.isBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                tempPersonDetail = repository.databaseGetMemberByEmail(myEmail)
            }
        }
    }

    fun onButtonClick(view: View) {
            val action = MyProfileFragmentDirections.actionMyProfileFragmentToShowMyNotesFragment()
            view.findNavController().navigate(action)
    }
}