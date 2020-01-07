package com.thelegendofawizard.writesomething.ui.members

import android.view.View
import androidx.navigation.findNavController
import com.thelegendofawizard.writesomething.utils.ButtonClickInterface
import com.thelegendofawizard.writesomething.utils.toast

data class PersonDetailModel (val email:String?,var name:String?,val about:String?, val url:String?):ButtonClickInterface{

    override fun onButtonClick(view: View) {
        val action = MembersFragmentDirections.actionMembersFragmentToShowMemberNotesFragment(email!!)
        view.findNavController().navigate(action)
    }
}