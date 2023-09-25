package com.example.findingqueen.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findingqueen.MainActivity
import com.example.findingqueen.R
import com.example.findingqueen.databinding.FragmentInfoBinding
import com.example.findingqueen.utils.Constants
import com.example.findingqueen.utils.addRemoveFragment

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private val fragmentMainContainer = R.id.main_fragment_container
    private lateinit var gameFragment: GameFragment
//    private lateinit var resultFragment: ResultFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater,container,false)
        resetDataForNewGame()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameFragment = GameFragment()
//        resultFragment = ResultFragment()

        binding.btnStartGame.setOnClickListener {
            activity?.addRemoveFragment(true,gameFragment,fragmentMainContainer,true)
        }
    }

    //      Resetting data for next Game Cycle.
    private fun resetDataForNewGame(){
//        activity?.addRemoveFragment(false,gameFragment,fragmentMainContainer)
//        activity?.addRemoveFragment(false,resultFragment,fragmentMainContainer)

        Constants.PLANETS = arrayListOf()
        Constants.VEHICLES = arrayListOf()
        Constants.planetCounter = 0
        Constants.TIME_TAKEN = 0
    }

}