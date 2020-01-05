package com.thelegendofawizard.writesomething.ui.members

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.thelegendofawizard.writesomething.ProfilePic
import com.thelegendofawizard.writesomething.R
import com.thelegendofawizard.writesomething.databinding.MembersItemBinding

class MembersListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PersonDetailModel>() {

        override fun areItemsTheSame(oldItem: PersonDetailModel, newItem: PersonDetailModel): Boolean {
            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: PersonDetailModel, newItem: PersonDetailModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MembersListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.members_item,
                parent,
                false
            ),interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MembersListViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<PersonDetailModel>) {
        differ.submitList(list)
    }

    class MembersListViewHolder
    constructor(
        private val membersItemBinding: MembersItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(membersItemBinding.root) {

        fun bind(item:PersonDetailModel) = with(membersItemBinding) {
            membersItemBinding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            membersItemBinding.personDetailModel = item
            membersItemBinding.executePendingBindings()
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: PersonDetailModel)
    }
}
