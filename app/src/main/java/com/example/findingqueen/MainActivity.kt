package com.example.findingqueen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.findingqueen.databinding.ActivityMainBinding
import com.example.findingqueen.fragments.GameFragment
import com.example.findingqueen.fragments.InfoFragment
import com.example.findingqueen.models.viewmodels.MainViewModel
import com.example.findingqueen.models.viewmodels.ViewModelFactory
import com.example.findingqueen.repository.MainRepository
import com.example.findingqueen.service.ApiRetrofitClient
import com.example.findingqueen.utils.addRemoveFragment
import com.example.findingqueen.utils.isNetworkAvailable
import com.example.findingqueen.utils.resetDataForNewGame

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val fragmentMainContainer = R.id.main_fragment_container
    private lateinit var callBack: OnBackPressedCallback

    companion object{
        private lateinit var infoFragment: InfoFragment
        private lateinit var gameFragment: GameFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        infoFragment = InfoFragment()
        gameFragment = GameFragment()

        setUpActionBar()

        viewModel = ViewModelProvider(this,ViewModelFactory(MainRepository(ApiRetrofitClient.getInstance())))[MainViewModel::class.java]

        checkNetwork()

        callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                addRemoveFragment(true,infoFragment,fragmentMainContainer)
            }
        }
        onBackPressedDispatcher.addCallback(
            this,
            callBack
        )

    }

    private fun setUpActionBar(){
        setSupportActionBar(binding.toolbarMainActivity)
        supportActionBar?.title = resources.getString(R.string.app_name_header)
    }


    private fun checkNetwork(){
        if (isNetworkAvailable(this@MainActivity)){
            binding.mainFragmentContainer.visibility = View.VISIBLE
            binding.errorScreenMain.root.visibility = View.GONE
            addRemoveFragment(true, infoFragment,fragmentMainContainer)
        }else{
            binding.errorScreenMain.tvErrorMessage.text = resources.getString(R.string.no_internet)
            binding.mainFragmentContainer.visibility = View.GONE
            binding.errorScreenMain.root.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_reset_game,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_reset -> {
                resetDataForNewGame()
                checkNetwork()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}