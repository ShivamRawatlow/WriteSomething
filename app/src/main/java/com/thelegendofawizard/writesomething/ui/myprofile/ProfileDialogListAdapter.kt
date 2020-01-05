package com.thelegendofawizard.writesomething.ui.myprofile

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.thelegendofawizard.writesomething.ProfilePic
import com.thelegendofawizard.writesomething.R
import com.thelegendofawizard.writesomething.databinding.ProfilepicTemBinding

class ProfileDialogListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProfilePic>() {

        override fun areItemsTheSame(oldItem: ProfilePic, newItem: ProfilePic): Boolean {
            return oldItem.faceName == newItem.faceName
        }

        override fun areContentsTheSame(oldItem: ProfilePic, newItem: ProfilePic): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MembersListViewHolder(

            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.profilepic_tem,
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

    fun submitList(list: List<ProfilePic>) {
        differ.submitList(list)
    }

    class MembersListViewHolder
    constructor(
        private val profilepicTemBinding: ProfilepicTemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(profilepicTemBinding.root) {

        fun bind(item:ProfilePic) = with(profilepicTemBinding) {
            profilepicTemBinding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            profilepicTemBinding.profilePic = item
            profilepicTemBinding.executePendingBindings()
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ProfilePic)
    }
}
