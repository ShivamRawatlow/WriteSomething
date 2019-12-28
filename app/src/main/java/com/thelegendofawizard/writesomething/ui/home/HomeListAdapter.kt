package com.thelegendofawizard.writesomething.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.thelegendofawizard.writesomething.MyNote
import com.thelegendofawizard.writesomething.R
import com.thelegendofawizard.writesomething.databinding.HomeItemBinding
import com.thelegendofawizard.writesomething.databinding.ShownotesItemBinding


class HomeListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MyNote>() {

        override fun areItemsTheSame(oldItem: MyNote, newItem: MyNote): Boolean {
            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: MyNote, newItem: MyNote): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return HomeListViewHolder(

            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.home_item,
                parent,
                false
            ),interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeListViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<MyNote>) {
        differ.submitList(list)
    }

    class HomeListViewHolder
    constructor(
        private val homeItemBinding: HomeItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(homeItemBinding.root) {

        fun bind(item:MyNote) = with(homeItemBinding) {
            homeItemBinding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            homeItemBinding.note = item
            homeItemBinding.executePendingBindings()
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: MyNote)
    }
}
