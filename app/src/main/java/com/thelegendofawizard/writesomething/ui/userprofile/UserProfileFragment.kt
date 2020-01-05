package com.thelegendofawizard.writesomething.ui.userprofile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.thelegendofawizard.writesomething.R
import com.thelegendofawizard.writesomething.databinding.UserProfileFragmentBinding
import com.thelegendofawizard.writesomething.ui.myprofile.MyProfileViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class UserProfileFragment : Fragment(),KodeinAware {


    override val kodein by kodein()
    private val userProfileViewModelFactory : UserProfileViewModelFactory by instance()
    lateinit var userEmail:String

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = UserProfileFragmentBinding.inflate(layoutInflater)

        val args = UserProfileFragmentArgs.fromBundle(arguments!!)
        userEmail = args.userEmail!!

        viewModel = ViewModelProvider(this,userProfileViewModelFactory).get(UserProfileViewModel::class.java)
        viewModel.getData(userEmail)

        binding.userProfileViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.tempPersonDetail.observe(this, Observer {
            viewModel.name.value = it.name
            viewModel.email.value = it.email
            viewModel.about.value = it.about
        })
    }

}
