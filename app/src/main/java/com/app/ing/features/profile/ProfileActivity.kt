package com.app.ing.features.profile

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.ing.R
import com.app.ing.base.models.ResultOf
import com.app.ing.base.models.UserAlbumItem
import com.app.ing.databinding.ActivityProfileBinding
import com.app.ing.features.user.UserListViewModel
import com.app.ing.utilities.UtilsFunctions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        observeData()
        performLogic()
    }


    private fun performLogic() {
        if (UtilsFunctions.isNetworkAvailable(this)) {
            getUserAlbums(intent.getIntExtra("id", 0))
            setUserData(intent.getStringExtra("title"))
        } else
            UtilsFunctions.showToast(this, getString(R.string.no_internet_connection))
    }

    private fun observeData() {
        viewModel.userAlbumsResponse.observe(this) {
            when (it) {
                is ResultOf.Loading -> {
                    showProgress()
                }
                is ResultOf.Success -> {
                    setupAlbumsRecyclerView(it.data)
                    hideProgress()
                }
                is ResultOf.Failure -> {
                    UtilsFunctions.showToast(this, it.message.toString())
                    hideProgress()
                }
            }
        }
    }

    private fun setUserData(userItem: String?) {
        binding.tvUserName.text = userItem
    }

    private fun setupAlbumsRecyclerView(data: List<UserAlbumItem>) {
        binding.rvAlbums.apply {
            adapter = AlbumsAdapter(data)
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun getUserAlbums(userId: Int) {
        if (UtilsFunctions.isNetworkAvailable(this)) {
            viewModel.getUserAlbums(userId)
        } else
            UtilsFunctions.showToast(this, getString(R.string.no_internet_connection))
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