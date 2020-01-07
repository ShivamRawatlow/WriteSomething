package com.thelegendofawizard.writesomething.ui.showmynotes

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
import kotlinx.android.synthetic.main.show_my_notes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ShowMyNotesFragment : Fragment(),KodeinAware,ShowMyNotesListAdapter.Interaction {

    override val kodein by kodein()
    private val showMyNotesViewModelFactory :ShowMyNotesViewModelFactory by instance()

    private lateinit var viewModel: ShowMyNotesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_my_notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,showMyNotesViewModelFactory).get(ShowMyNotesViewModel::class.java)


        val adapter =  ShowMyNotesListAdapter(this@ShowMyNotesFragment)
        editnotes_recyclerview.apply {
            this.adapter = adapter
        }

        viewModel.notesList.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNotes_Button.setOnClickListener {
            val action = ShowMyNotesFragmentDirections.actionShowMyNotesFragmentToAddNoteFragment(
                "",true,viewModel.myName!!,viewModel.myEmail!!,"","",true
            )
            findNavController().navigate(action)
        }
    }

    override fun onItemSelected(position: Int, item: MyNote) {

            val action = ShowMyNotesFragmentDirections.actionShowMyNotesFragmentToAddNoteFragment(
                item.id,false,item.name,item.email,item.body,item.title,item.visibility)

            findNavController().navigate(action)

    }

}
