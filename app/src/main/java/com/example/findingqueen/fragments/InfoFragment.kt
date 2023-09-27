package com.example.findingqueen.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findingqueen.R
import com.example.findingqueen.databinding.FragmentInfoBinding
import com.example.findingqueen.utils.addRemoveFragment
import com.example.findingqueen.utils.resetDataForNewGame

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private val fragmentMainContainer = R.id.main_fragment_container
    private lateinit var gameFragment: GameFragment
    private lateinit var resultFragment: ResultFragment


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
        resultFragment = ResultFragment()


        binding.btnStartGame.setOnClickListener {
            activity?.addRemoveFragment(true,gameFragment,fragmentMainContainer)
        }
    }

}