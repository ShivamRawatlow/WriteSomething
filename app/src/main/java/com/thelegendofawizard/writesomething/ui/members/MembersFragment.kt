package com.thelegendofawizard.writesomething.ui.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thelegendofawizard.writesomething.R
import com.thelegendofawizard.writesomething.utils.toast
import kotlinx.android.synthetic.main.members_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MembersFragment : Fragment(),KodeinAware,MembersListAdapter.Interaction {

    override val kodein by kodein()
    private val membersViewModelFactory :MembersViewModelFactory by instance()
    private lateinit var membersViewModel: MembersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.members_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        membersViewModel = ViewModelProvider(this,membersViewModelFactory).get(MembersViewModel::class.java)

        val adapter =  MembersListAdapter(this@MembersFragment)
        members_recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        membersViewModel.alldatabasemembers.observe(this, Observer {

            val list = mutableListOf<PersonDetailModel>()
            for(item in it){
                list.add(PersonDetailModel(item.email,item.name,item.about,item.url))
            }
            adapter.submitList(list)
        })
    }

    override fun onItemSelected(position: Int, item: PersonDetailModel) {

        if(membersViewModel.myEmail == item.email) {
            val action = MembersFragmentDirections.actionMembersFragmentToMyProfileFragment()
            findNavController().navigate(action)
        }
        else {
            val action = MembersFragmentDirections.actionMembersFragmentToUserProfileFragment(item.email)
            findNavController().navigate(action)
        }
    }

}


