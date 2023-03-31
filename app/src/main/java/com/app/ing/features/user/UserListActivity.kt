package com.app.ing.features.user

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.ing.R
import com.app.ing.base.models.ResultOf
import com.app.ing.base.models.UserItem
import com.app.ing.databinding.ActivityUserListBinding
import com.app.ing.utilities.UtilsFunctions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserListBinding
    private lateinit var  viewModel: UserListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[UserListViewModel::class.java]
        setContentView(binding.root)
        observeData()
        performLogic()
    }

    private fun performLogic() {
        if (UtilsFunctions.isNetworkAvailable(this)) {
            viewModel.getAllUsers()
        } else
            UtilsFunctions.showToast(this, getString(R.string.no_internet_connection))
    }

    private fun observeData() {
        viewModel.usersResponse.observe(this) {
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

    private fun setupAlbumsRecyclerView(data: List<UserItem>) {
        binding.rvUser.apply {
            adapter = UserListAdapter(data)
        }
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }
}