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
import com.bumptech.glide.Glide
import com.thelegendofawizard.writesomething.ProfilePic
import com.thelegendofawizard.writesomething.R
import com.thelegendofawizard.writesomething.databinding.MyProfileFragmentBinding
import com.thelegendofawizard.writesomething.utils.toast
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.my_profile_fragment.*
import kotlinx.android.synthetic.main.profile_pic_dialog.*
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
    var dbFaceName:String? = null


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
            viewModel.faceName.value = it.faceName
            dbFaceName = it.faceName
        })

        viewModel.faceName.observe(this, Observer {
            if(it.isNotBlank()){
                CoroutineScope(IO).launch {
                    viewModel.faceData = viewModel.repository.databaseGetPicByFaceNamec(it)
                }
            }
        })

        myprofile_imagebutton.setOnClickListener {
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

        viewModel.repository.ListProfilePics.observe(this, Observer {
            adapter.submitList(it)
        })

        /*val circleImage1:CircleImageView = view.findViewById(R.id.circleImageView1)
        val circleImage2:CircleImageView = view.findViewById(R.id.circleImageView2)
        val circleImage3:CircleImageView = view.findViewById(R.id.circleImageView3)
        val circleImage4:CircleImageView = view.findViewById(R.id.circleImageView4)
        val circleImage5:CircleImageView = view.findViewById(R.id.circleImageView5)
        val circleImage6:CircleImageView = view.findViewById(R.id.circleImageView6)
        val circleImage7:CircleImageView = view.findViewById(R.id.circleImageView7)

        viewModel.repository.ListProfilePics.observe(this, Observer {

            if(it.isNotEmpty()) {

                Glide.with(this)
                    .load(it[0].faceData)
                    .into(circleImage1)

                Glide.with(this)
                    .load(it[1].faceData)
                    .into(circleImage2)

                Glide.with(this)
                    .load(it[2].faceData)
                    .into(circleImage3)

                Glide.with(this)
                    .load(it[3].faceData)
                    .into(circleImage4)

                Glide.with(this)
                    .load(it[4].faceData)
                    .into(circleImage5)

                Glide.with(this)
                    .load(it[5].faceData)
                    .into(circleImage6)

                Glide.with(this)
                    .load(it[6].faceData)
                    .into(circleImage7)
            }

            *//*Glide.with(this)
                .asBitmap()
                .centerCrop()
                .load(it[1].faceData)
                .into(circleImageView2)

            Glide.with(this)
                .asBitmap()
                .centerCrop()
                .load(it[2].faceData)
                .into(circleImageView3)

            Glide.with(this)
                .asBitmap()
                .centerCrop()
                .load(it[3].faceData)
                .into(circleImageView4)

            Glide.with(this)
                .asBitmap()
                .centerCrop()
                .load(it[4].faceData)
                .into(circleImageView5)

            Glide.with(this)
                .asBitmap()
                .centerCrop()
                .load(it[5].faceData)
                .into(circleImageView6)

            Glide.with(this)
                .asBitmap()
                .centerCrop()
                .load(it[6].faceData)
                .into(circleImageView7)*//*

        })*/
        val alertDialog = alert.create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
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

            val uifaceName = viewModel.faceName.value
            uifaceName.let {
                if(it.isNullOrBlank()){
                    if (uiAbout != dbAbout)
                        viewModel.repository.updateMemberDetail(
                            viewModel.myEmail,
                            "faceName",
                            it!!
                        )
                }
            }
        }
        super.onDestroyView()
    }

    override fun onItemSelected(position: Int, item: ProfilePic) {
        viewModel.faceName.value = item.faceName
        context!!.toast("ClickWorking!! : ${item.faceName}")
    }
}