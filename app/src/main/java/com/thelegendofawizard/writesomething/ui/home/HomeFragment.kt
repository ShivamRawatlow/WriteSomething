package com.thelegendofawizard.writesomething.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thelegendofawizard.writesomething.MyNote
import com.thelegendofawizard.writesomething.R
import kotlinx.android.synthetic.main.home_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(),KodeinAware,HomeListAdapter.Interaction {

    override val kodein by kodein()
    private val homeViewModelFactory :HomeViewModelFactory by instance()

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.home_fragment, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this,homeViewModelFactory).get(HomeViewModel::class.java)

        val adapter =  HomeListAdapter(this@HomeFragment)
        home_recyclerview.apply {
            this.adapter = adapter
        }

        viewModel.notesList.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    override fun onItemSelected(position: Int, item: MyNote) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}