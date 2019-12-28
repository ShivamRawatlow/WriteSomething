package com.thelegendofawizard.writesomething.ui.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thelegendofawizard.writesomething.databinding.MyProfileFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MyProfileFragment : Fragment() ,KodeinAware{

    override val kodein by kodein()
    private val myProfileViewModelFactory : MyProfileViewModelFactory by instance()
    var dbName:String? = null

    var dbAbout:String? = null


    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MyProfileFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this,myProfileViewModelFactory).get(MyProfileViewModel::class.java)
        binding.myProfileViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tempPersonDetail.observe(this, Observer {
            viewModel.name.value = it.name
            dbName = it.name
            viewModel.email.value = it.email
            viewModel.about.value = it.about
            dbAbout = it.about
        })
    }

    override fun onDestroyView() {

        val uiName = viewModel.name.value

        if(uiName!!.isNotBlank()) {
            uiName.let {
                if (uiName != dbName)
                    viewModel.repository.updateMemberDetail(
                        viewModel.myEmail,
                        "name",
                        uiName
                    )
            }

            val uiAbout = viewModel.about.value

            uiAbout.let {
                if (uiAbout != dbAbout)
                    viewModel.repository.updateMemberDetail(
                        viewModel.myEmail,
                        "about",
                        uiAbout!!
                    )
            }
        }
        super.onDestroyView()
    }
}