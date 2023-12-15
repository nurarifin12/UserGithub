package com.example.usergithub.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.usergithub.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailUserActivity : AppCompatActivity() {

    companion object {
        val EXTRA_USERNAME = "extra_username"
        val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var sectionPagerAdapter: SectionPagerAdapter
    private lateinit var viewModel : DetailUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val Username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME,Username)

        showLoading(true)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        viewModel.setUserDetail(Username.toString())
        viewModel.getUserDetail().observe(this,{


            if (it != null){
                binding.apply {
                    showLoading(false)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollower.text = "${it.followers} followers"
                    tvFollowing.text =  "${it.following} following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }
            }

        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null) {
                  if (count> 0) {
                      binding.toggleButton3.isChecked=true
                      _isChecked = true
                  }  else {
                      binding.toggleButton3.isChecked=false
                      _isChecked = false
                  }
                }
            }
        }

        binding.toggleButton3.setOnClickListener{
            _isChecked = !_isChecked

            if (_isChecked){
                viewModel.addToFavorit(username, id)
            }else{
                viewModel.removeFavorit(id)
            }
            binding.toggleButton3.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter( this,  supportFragmentManager, bundle)
            binding.apply {
                viewPager.adapter = sectionPagerAdapter
                tabs.setupWithViewPager(viewPager)
            }
    }



    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }


}