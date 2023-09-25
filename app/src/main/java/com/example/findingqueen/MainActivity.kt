package com.example.findingqueen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.findingqueen.databinding.ActivityMainBinding
import com.example.findingqueen.fragments.InfoFragment
import com.example.findingqueen.models.viewmodels.MainViewModel
import com.example.findingqueen.models.viewmodels.ViewModelFactory
import com.example.findingqueen.repository.MainRepository
import com.example.findingqueen.service.ApiResponse
import com.example.findingqueen.service.ApiRetrofitClient
import com.example.findingqueen.utils.Constants
import com.example.findingqueen.utils.addRemoveFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val fragmentMainContainer = R.id.main_fragment_container

    companion object{
        private lateinit var infoFragment: InfoFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        infoFragment =  InfoFragment()

        setUpActionBar()

        viewModel = ViewModelProvider(this,ViewModelFactory(MainRepository(ApiRetrofitClient.getInstance())))[MainViewModel::class.java]

        addRemoveFragment(true,infoFragment,fragmentMainContainer)

    }

    private fun setUpActionBar(){
        setSupportActionBar(binding.toolbarMainActivity)
        supportActionBar?.title = resources.getString(R.string.app_name_header)
    }

}