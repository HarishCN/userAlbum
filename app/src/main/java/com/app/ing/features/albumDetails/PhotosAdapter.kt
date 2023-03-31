package com.app.ing.features.albumDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ing.R
import com.app.ing.base.models.AlbumPhotoItem
import com.app.ing.databinding.ItemPhotoLayoutBinding
import com.app.ing.features.fullScreen.FullScreenActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders


class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {
    private val data = ArrayList<AlbumPhotoItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newData: List<AlbumPhotoItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosViewHolder {
        val binding =
            ItemPhotoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class PhotosViewHolder(private val binding: ItemPhotoLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlbumPhotoItem) {
            val context = binding.root.context
            val url = GlideUrl(
                item.thumbnailUrl, LazyHeaders.Builder()
                    .addHeader("User-Agent", "your-user-agent")
                    .build()
            )
            Glide.with(context).load(url).dontAnimate()
                .placeholder(R.drawable.loader)
                .into(binding.ivPhoto)

            binding.root.setOnClickListener {
                context.startActivity(Intent(context, FullScreenActivity::class.java).apply {
                    putExtra("url", item.url)
                    putExtra("title", item.title)
                })
            }
        }

    }
}