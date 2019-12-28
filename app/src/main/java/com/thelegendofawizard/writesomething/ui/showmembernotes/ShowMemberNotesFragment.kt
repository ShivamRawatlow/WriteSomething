package com.thelegendofawizard.writesomething.ui.showmembernotes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.thelegendofawizard.writesomething.MyNote

import com.thelegendofawizard.writesomething.R
import kotlinx.android.synthetic.main.show_member_notes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ShowMemberNotesFragment : Fragment(),KodeinAware,ShowMemberNotesListAdapter.Interaction {

    override val kodein by kodein()
    private val showMemberNotesViewModelFactory :ShowMemberNotesViewModelFactory by instance()

    private lateinit var viewModel: ShowMemberNotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_member_notes_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = ShowMemberNotesFragmentArgs.fromBundle(arguments!!)

        viewModel = ViewModelProvider(this,showMemberNotesViewModelFactory).get(ShowMemberNotesViewModel::class.java)
        viewModel.getNotes(args.email)

        val adapter =  ShowMemberNotesListAdapter(this@ShowMemberNotesFragment)
        showmembernotes_recyclerview.apply {
            this.adapter = adapter
        }

        viewModel.notesList.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    override fun onItemSelected(position: Int, item: MyNote) {
        val action = ShowMemberNotesFragmentDirections.actionShowMemberNotesFragmentToOpenNoteFragment(
            item.name,item.title,item.body)
        findNavController().navigate(action)
    }

}
