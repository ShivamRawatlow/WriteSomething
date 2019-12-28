package com.thelegendofawizard.writesomething.ui.opennote


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.thelegendofawizard.writesomething.databinding.OpenNoteFragmentBinding

class OpenNoteFragment : Fragment() {


    private lateinit var viewModel: OpenNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = OpenNoteFragmentBinding.inflate(layoutInflater)
        val args = OpenNoteFragmentArgs.fromBundle(arguments!!)
        binding.name = args.name
        binding.title = args.title
        binding.body = args.body
        return binding.root
    }



}
