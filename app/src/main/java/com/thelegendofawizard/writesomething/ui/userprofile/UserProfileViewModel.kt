package com.thelegendofawizard.writesomething.ui.userprofile

import android.app.Application
import android.provider.ContactsContract
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.thelegendofawizard.writesomething.PersonDetail
import com.thelegendofawizard.writesomething.Repository
import com.thelegendofawizard.writesomething.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfileViewModel(val application: Application, val repository: Repository):ViewModel() {


    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var about = MutableLiveData<String>()
    var url = MutableLiveData<String>()

    var tempPersonDetail: LiveData<PersonDetail> = MutableLiveData<PersonDetail>()


    fun getData(userEmail:String) {
        viewModelScope.launch(Dispatchers.IO) {
            tempPersonDetail = repository.getMemberByEmail(userEmail)
        }
    }

    fun onButtonClick(view: View) {

        email.value.let {
            val action = UserProfileFragmentDirections.actionUserProfileFragmentToShowMemberNotesFragment(it!!)
            view.findNavController().navigate(action)
        }

    }

}