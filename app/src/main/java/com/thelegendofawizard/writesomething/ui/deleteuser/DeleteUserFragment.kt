package com.thelegendofawizard.writesomething.ui.deleteuser


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

import com.thelegendofawizard.writesomething.R
import com.thelegendofawizard.writesomething.ui.googlesignin.GoogleSignInActivity
import com.thelegendofawizard.writesomething.utils.toast
import kotlinx.android.synthetic.main.delete_user_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import kotlin.system.exitProcess

class DeleteUserFragment : Fragment() , KodeinAware {

    override val kodein by kodein()
    private val deleteUserViewModelFactory:DeleteUserViewModelFactory by instance()

    private lateinit var viewModel: DeleteUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.delete_user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,deleteUserViewModelFactory).get(DeleteUserViewModel::class.java)

        val colorValue = ContextCompat.getColor(context!!,R.color.dullWhite)
        delete_button.setBackgroundColor(colorValue)

        delete_button.setOnClickListener {
            viewModel.deleteUser()

            Handler().postDelayed({
                activity?.let{
                    val intent = Intent (it, GoogleSignInActivity::class.java)
                    it.startActivity(intent)
                }
            }, 1000)

        }

        you_switch.setOnClickListener {
            activateDeactivateButton()
        }

        are_switch.setOnClickListener {
            activateDeactivateButton()
        }

        sure_switch.setOnClickListener {
            activateDeactivateButton()
        }

    }

    fun activateDeactivateButton(){
        if(are_switch.isChecked and you_switch.isChecked and sure_switch.isChecked){
            delete_button.isEnabled = true
            val colorValue = ContextCompat.getColor(context!!,R.color.buttonRed)
            delete_button.setBackgroundColor(colorValue)
        }
        else{
            delete_button.isEnabled = false
            val colorValue = ContextCompat.getColor(context!!,R.color.dullWhite)
            delete_button.setBackgroundColor(colorValue)
        }
    }
}
