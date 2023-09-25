package com.example.findingqueen.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findingqueen.R
import com.example.findingqueen.adapters.DestinationListAdapter
import com.example.findingqueen.databinding.FragmentGameBinding
import com.example.findingqueen.models.viewmodels.MainViewModel
import com.example.findingqueen.service.ApiResponse
import com.example.findingqueen.utils.Constants
import com.example.findingqueen.utils.addRemoveFragment
import com.example.findingqueen.utils.hideProgressDialog
import com.example.findingqueen.utils.showProgressDialog

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: MainViewModel
    private val fragmentMainContainer = R.id.main_fragment_container
    private lateinit var resultFragment :ResultFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        resultFragment = ResultFragment()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getGameData()

        binding.btnResult.setOnClickListener {
            if (binding.btnResult.isEnabled){
                Log.e("Game", Constants.PLANETS.toString())
                Log.e("Game",Constants.VEHICLES.toString())
                activity?.addRemoveFragment(true,resultFragment,fragmentMainContainer)
            }else{
                Toast.makeText(context,"Select least 4 planets", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvSelect4Planets.text = resources.getString(R.string.select_4_planets)
        binding.tvTimeTaken.text = resources.getString(R.string.time_taken,"0")
        binding.btnResult.isEnabled = false
        binding.btnResult.alpha = 0.5f

    }

    private fun findBtnCheck(){
        if (Constants.planetCounter >= 4){
            binding.tvSelect4Planets.text = resources.getString(R.string.ready_for_hunt)
            binding.tvSelect4Planets.setTextColor(ContextCompat.getColor(requireContext(),R.color.blue))

            binding.btnResult.isEnabled = true
            binding.btnResult.alpha = 1.0f
        }else{
            binding.btnResult.isEnabled = false
            binding.btnResult.alpha = 0.5f
        }
    }

    private fun getGameData(){

        showProgressDialog(requireContext())

        viewModel.tokenResponse()
        viewModel.tokenResponse.observe(
            viewLifecycleOwner, Observer {
                when(it){
                    is ApiResponse.Loading -> {
                        Log.e("Loading","Loading...")
                    }
                    is ApiResponse.Success -> {
                        Log.e("Success","Success ${it.value}")
                        Constants.TOKEN = it.value.token
                        viewModel.vehiclesResponse()
                    }
                    is ApiResponse.Failure -> {
                        hideProgressDialog()
                        Log.e("Failure","Failed")
                    }
                }
            }
        )

        viewModel.vehiclesResponse.observe(
            viewLifecycleOwner, Observer {
                when(it){
                    is ApiResponse.Loading -> {
                        Log.e("Loading","Loading...")
                    }
                    is ApiResponse.Success -> {
                        Log.e("Success","Success ${it.value} ")
                        Constants.VEHICLES = it.value
                        viewModel.planetsResponse()
                    }
                    is ApiResponse.Failure -> {
                        hideProgressDialog()
                        Log.e("Failure","Failed")
                    }
                }
            }
        )

        viewModel.planetsResponse.observe(
            viewLifecycleOwner, Observer {
                when(it){
                    is ApiResponse.Loading -> {
                        Log.e("Loading","Loading...")
                    }
                    is ApiResponse.Success -> {
                        Log.e("Success","Success ${it.value}")
                        Constants.PLANETS = it.value
                        setPlanets()
                    }
                    is ApiResponse.Failure -> {
                        hideProgressDialog()
                        Log.e("Failure","Failed")
                    }
                }
            }
        )

    }

    private fun displayData(){
//        Launching Recycler view
        binding.rvDestination.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        binding.rvDestination.setHasFixedSize(true)
        val adapter =  DestinationListAdapter(requireContext(), Constants.PLANETS)

        adapter.setOnChangeListener(
            object : DestinationListAdapter.OnChangeListener{
                override fun onChange() {
                    binding.rvDestination.invalidate()
                    binding.tvTimeTaken.text = resources.getString(R.string.time_taken,Constants.TIME_TAKEN.toString())
                    findBtnCheck()
                }
            }
        )

        binding.rvDestination.adapter = adapter

    }

    private fun setPlanets(){
        hideProgressDialog()
        for (i in Constants.PLANETS.indices){
            Constants.PLANETS[i].selectedPos = -1
        }
        displayData()
    }

}