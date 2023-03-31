package com.app.ing.features.user

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ing.base.models.UserItem
import com.app.ing.databinding.ItemUserLayoutBinding
import com.app.ing.features.profile.ProfileActivity

class UserListAdapter(
    private val data: List<UserItem>
) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListViewHolder {
        val binding =
            ItemUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class UserListViewHolder(private val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserItem) {
            val context = binding.root.context
            binding.tvUserName.text = item.name
            binding.tvEmail.text = item.email
            binding.tvUserItemId.text = item.name.subSequence(0, 1)
            binding.root.setOnClickListener {
                context.startActivity(
                    Intent(
                        context,
                        ProfileActivity::class.java
                    ).apply {
                        putExtra("id", item.id)
                        putExtra("title", item.name)
                    })
            }
        }

    }
}