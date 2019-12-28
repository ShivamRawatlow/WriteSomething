package com.thelegendofawizard.writesomething.ui.addnote

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.thelegendofawizard.writesomething.MyNote

import com.thelegendofawizard.writesomething.databinding.AddNoteFragmentBinding
import kotlinx.android.synthetic.main.add_note_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

class AddNoteFragment : Fragment(),KodeinAware {

    override val kodein by kodein()
    private val addNoteViewModelFactory : AddNoteViewModelFactory by instance()
    private lateinit var viewModel: AddNoteViewModel
    private var newNote = false

    private var dbBody:String? = null
    private var dbTitle:String? = null
    private var dbId :String? = null
    private var dbEmail:String? = null
    private lateinit var dbName:String

    private var noteVisibility = true
    private var dbVisibility = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = AddNoteFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this,addNoteViewModelFactory).get(AddNoteViewModel::class.java)

            val args = AddNoteFragmentArgs.fromBundle(arguments!!)
            newNote = args.newnote
            dbName = args.name
            viewModel.name = args.name
        if(!newNote) {
            viewModel.body.value = args.body
            dbBody = args.body
            viewModel.title.value = args.title
            dbTitle = args.title
            dbId = args.id
            dbEmail = args.email
            dbVisibility = args.visibility
            noteVisibility = args.visibility
        }

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(noteVisibility){
            visibility_button.text = "VISIBLE"
            visibility_button.setBackgroundColor(Color.rgb(0.0f,0.8f,0.0f))
        }
        else{
            visibility_button.text = "HIDDEN"
            visibility_button.setBackgroundColor(Color.rgb(0.8f,0.0f,0.0f))
        }


        visibility_button.setOnClickListener {
            if(noteVisibility){
                visibility_button.text = "HIDDEN"
                visibility_button.setBackgroundColor(Color.rgb(0.8f,0.0f,0.0f))
                noteVisibility = false
            }
            else{
                visibility_button.text = "VISIBLE"
                visibility_button.setBackgroundColor(Color.rgb(0.0f,0.8f,0.0f))
                noteVisibility = true
            }

        }

    }

    override fun onDestroyView() {

        if (newNote) {
            if (!viewModel.body.value.isNullOrBlank() || !viewModel.title.value.isNullOrBlank()) {

                val myNote = MyNote(
                    UUID.randomUUID().toString(),
                    viewModel.myEmail,
                    viewModel.name,
                    viewModel.title.value,
                    viewModel.body.value,
                    noteVisibility
                )
                viewModel.saveNote(myNote)
            }
        }

        else{
            if(dbBody != viewModel.body.value || dbTitle != viewModel.title.value || dbVisibility != noteVisibility )
            {
                val myNote = MyNote(
                    dbId!!,
                    viewModel.myEmail,
                    viewModel.name,
                    viewModel.title.value,
                    viewModel.body.value,
                    noteVisibility
                )

                if(viewModel.title.value.isNullOrBlank() && viewModel.body.value.isNullOrBlank())
                {
                    //if the user is in editmode and he leaves both title and body blank
                    //the the note will be deleted from database
                    viewModel.deleteNote(myNote)
                }
                else
                {
                    viewModel.updateNote(myNote)
                }

            }
        }
        super.onDestroyView()
    }
}
