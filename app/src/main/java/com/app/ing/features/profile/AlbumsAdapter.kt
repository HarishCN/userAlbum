package com.app.ing.features.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ing.base.models.UserAlbumItem
import com.app.ing.databinding.ItemAlbumLayoutBinding
import com.app.ing.features.albumDetails.AlbumDetailsActivity

class AlbumsAdapter(
    private val data: List<UserAlbumItem>
) : RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumsViewHolder {
        val binding =
            ItemAlbumLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class AlbumsViewHolder(private val binding: ItemAlbumLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserAlbumItem) {

            val context = binding.root.context
            binding.tvAlbumName.text = item.title
            binding.root.setOnClickListener {
                context.startActivity(
                    Intent(
                        context,
                        AlbumDetailsActivity::class.java
                    ).apply {
                        putExtra("id", item.id)
                        putExtra("title", item.title)
                    })
            }
        }
    }
}