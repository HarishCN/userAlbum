package com.app.ing.features.fullScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.ing.R
import com.app.ing.databinding.ActivityFullScreenBinding
import com.bumptech.glide.Glide

class FullScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullScreenBinding
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initialization()
    }

    private fun initialization() {
        imageUrl = intent.getStringExtra("url").toString()
        Glide.with(this).load(intent.getStringExtra("url")).placeholder(R.drawable.loader)
            .into(binding.ivFullScreen)
        binding.tvImageName.text = intent.getStringExtra("title")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}