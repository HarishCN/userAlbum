package com.app.ing.features.albumDetails

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.ing.R
import com.app.ing.base.models.AlbumPhotoItem
import com.app.ing.base.models.ResultOf
import com.app.ing.databinding.ActivityAlbumDetailsBinding
import com.app.ing.features.profile.ProfileViewModel
import com.app.ing.utilities.UtilsFunctions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumDetailsBinding
    private lateinit var viewModel: AlbumDetailsViewModel
    private lateinit var photosAdapter: PhotosAdapter
    private val allPhotos = ArrayList<AlbumPhotoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetailsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AlbumDetailsViewModel::class.java]
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initView()
        observeData()
        performLogic()
    }

    private fun initView() {
        binding.tvToolbarTitle.text = intent.getStringExtra("title")
        setupPhotosRecyclerView()
    }

    private fun performLogic() {
        if (UtilsFunctions.isNetworkAvailable(this)) {
            viewModel.getAlbumPhotos(intent.getIntExtra("id", 0))
        } else
            UtilsFunctions.showToast(this, getString(R.string.no_internet_connection))
    }

    private fun observeData() {
        viewModel.albumPhotosResponse.observe(this) {
            when (it) {
                is ResultOf.Loading -> {
                    showProgress()
                }
                is ResultOf.Success -> {
                    allPhotos.addAll(it.data)
                    addDataToRecyclerView(it.data)
                    hideProgress()
                }
                is ResultOf.Failure -> {
                    UtilsFunctions.showToast(this, it.message.toString())
                    hideProgress()
                }
            }
        }
    }

    private fun addDataToRecyclerView(data: List<AlbumPhotoItem>) {
        photosAdapter.addData(data)
    }

    private fun setupPhotosRecyclerView() {
        photosAdapter = PhotosAdapter()
        binding.rvAlbumPhotos.apply {
            adapter = photosAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}