package com.thelegendofawizard.writesomething.ui.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.thelegendofawizard.writesomething.ProfilePic
import com.thelegendofawizard.writesomething.R
import com.thelegendofawizard.writesomething.databinding.MyProfileFragmentBinding
import com.thelegendofawizard.writesomething.utils.toast
import kotlinx.android.synthetic.main.my_profile_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MyProfileFragment : Fragment() ,KodeinAware,ProfileDialogListAdapter.Interaction{

    override val kodein by kodein()
    private val myProfileViewModelFactory : MyProfileViewModelFactory by instance()
    var dbName:String? = null

    var dbAbout:String? = null
    var dbUrl:String? = null
    lateinit var alertDialog:AlertDialog

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
            if(it!=null) {
               // context!!.toast("Holla")
                viewModel.name.value = it.name
                dbName = it.name
                viewModel.email.value = it.email
                viewModel.about.value = it.about
                dbAbout = it.about
                viewModel.url.value = it.url
                dbUrl = it.url
            }
        })

        myprofile_imagebutton.setOnClickListener {

            //context!!.toast(viewModel.myEmail!!)
            showDialog()
        }
    }

    fun showDialog(){
        val alert  = AlertDialog.Builder(context!!)
        val view = layoutInflater.inflate(R.layout.profile_pic_dialog, null)
        alert.setView(view)

        val profilepicRecyclerView:RecyclerView = view.findViewById(R.id.profile_pic_recyclerview)

        val adapter =  ProfileDialogListAdapter(this@MyProfileFragment)
        profilepicRecyclerView.apply {
            this.adapter = adapter
        }

        viewModel.repository.listProfilePics.observe(this, Observer {
            adapter.submitList(it)
        })

        alertDialog = alert.create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }

    override fun onDestroyView() {

        if(!viewModel.myEmail.isNullOrBlank()) {

            val uiName = viewModel.name.value
            if (!uiName.isNullOrBlank()) {

                if (uiName != dbName)
                    viewModel.repository.updateMemberDetail(
                        viewModel.myEmail.toString(),
                        "name",
                        uiName
                    )


                val uiAbout = viewModel.about.value
                if (uiAbout != null) {
                    if (uiAbout != dbAbout)
                        viewModel.repository.updateMemberDetail(
                            viewModel.myEmail.toString(),
                            "about",
                            uiAbout
                        )
                }


                val uiUrl = viewModel.url.value
                if (uiUrl != null) {
                    if (uiUrl != dbUrl)
                        viewModel.repository.updateMemberDetail(
                            viewModel.myEmail.toString(),
                            "url",
                            uiUrl
                        )
                }
            }
        }
        super.onDestroyView()
    }


    override fun onItemSelected(position: Int, item: ProfilePic) {
        viewModel.url.value = item.faceUrl
        alertDialog.dismiss()
    }
}