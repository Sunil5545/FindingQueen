package com.example.findingqueen.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.findingqueen.R
import com.example.findingqueen.databinding.FragmentResultBinding
import com.example.findingqueen.models.FindFalconeResponse
import com.example.findingqueen.models.viewmodels.MainViewModel
import com.example.findingqueen.service.ApiResponse
import com.example.findingqueen.utils.Constants
import com.example.findingqueen.utils.addRemoveFragment
import com.example.findingqueen.utils.hideProgressDialog
import com.example.findingqueen.utils.showProgressDialog

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private lateinit var viewModel: MainViewModel
    private val fragmentMainContainer = R.id.main_fragment_container
    private lateinit var infoFragment: InfoFragment

    private lateinit var token: String

    companion object{
        private lateinit var vehicles: ArrayList<String>
        private lateinit var planets: ArrayList<String>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token = Constants.TOKEN

        setResultData()
        infoFragment = InfoFragment()

        binding.btnStartAgain.setOnClickListener {

            activity?.addRemoveFragment(true,infoFragment,fragmentMainContainer)
        }

    }

    //    Set Data of Planets and Vehicles that User selected.
    private fun setResultData(){
        planets = arrayListOf()
        vehicles = arrayListOf()

        for (i in 0 until Constants.PLANETS.size){
            if (Constants.PLANETS[i].selectedPos != -1){
                planets.add(Constants.PLANETS[i].name)
                vehicles.add(Constants.VEHICLES[Constants.PLANETS[i].selectedPos].name)
            }
        }

        Log.e("Requestt", Constants.PLANETS.toString())
        Log.e("Requestt",Constants.VEHICLES.toString())

        getResult()
    }

    private fun getResult(){
        showProgressDialog(requireContext())

        val resultRequest = HashMap<String,Any>()
        resultRequest["token"] = token
        resultRequest["planet_names"] = planets
        resultRequest["vehicle_names"] = vehicles

        Log.e("Requestt",resultRequest.toString())

        viewModel.findFalconeResponse(resultRequest)
        viewModel.findFalconeResponse.observe(
            viewLifecycleOwner, Observer {
                when(it){
                    is ApiResponse.Loading -> {
                        Log.e("Loading","Loading...")
                    }
                    is ApiResponse.Success -> {
                        hideProgressDialog()
                        Log.e("Success","Success ${it.value}")
                        setSuccess(it.value)
                    }
                    is ApiResponse.Failure -> {
                        hideProgressDialog()
                        Log.e("Failure","Failed")
                    }
                }
            }
        )

    }

    private fun setSuccess(response : FindFalconeResponse){

        when(response.status){
            "success"-> {
                binding.tvQueenFound.visibility = View.VISIBLE
                binding.tvQueenNotFound.visibility = View.GONE
                binding.tvQueenFound.text = resources.getString(R.string.queen_found)
                binding.tvTimeTaken.text = resources.getString(R.string.time_taken,Constants.TIME_TAKEN.toString())
                binding.tvPlanetFound.text = resources.getString(R.string.planet_found,response.planetName)
            }
            else ->{

                binding.tvQueenFound.visibility = View.GONE
                binding.tvQueenNotFound.visibility = View.VISIBLE
                binding.tvQueenNotFound.text = resources.getString(R.string.queen_not_found)
                binding.tvTimeTaken.text = resources.getString(R.string.time_taken,Constants.TIME_TAKEN.toString())
                binding.tvPlanetFound.text = resources.getString(R.string.planet_not_found)
            }
        }
    }

}