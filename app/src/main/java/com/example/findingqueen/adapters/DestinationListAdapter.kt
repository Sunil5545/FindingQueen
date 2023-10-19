package com.example.findingqueen.adapters

import com.example.findingqueen.R
import com.example.findingqueen.utils.Constants

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findingqueen.models.Planets


class DestinationListAdapter(
    private val context: Context,
    private var list: ArrayList<Planets>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var onChangeListener: OnChangeListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_destination,
                    parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

// It sets the planet Images in Recycler View.
            when (position) {
                0 -> holder.planets.setImageResource(R.drawable.planet1)
                1 -> holder.planets.setImageResource(R.drawable.planet2)
                2 -> holder.planets.setImageResource(R.drawable.planet3)
                3 -> holder.planets.setImageResource(R.drawable.planet4)
                4 -> holder.planets.setImageResource(R.drawable.planet5)
                5 -> holder.planets.setImageResource(R.drawable.planet6)
                else -> holder.planets.setImageResource(R.drawable.ic_default_image)
            }


            holder.tvDestinationCount.text = model.name

            holder.vehicles.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            holder.vehicles.setHasFixedSize(true)
            val adapter = VehicleListAdapter(context, Constants.VEHICLES, position)


/*
* This is callBack functionality to Refresh this Recycler view to update
* the Vehicle Count Data.
* */
            adapter.setOnClickListener(
                object : VehicleListAdapter.OnClickListener{
                    override fun onClick(position: Int) {
                        checkSelectedPlanets()
                        onChangeListener?.onChange()
                        notifyDataSetChanged()
                        holder.vehicles.invalidate()
                    }
                }
            )

            holder.vehicles.adapter = adapter

        }
    }

    /*
    * This is to keep counts of the Destination View and only allow 4 planets
    * to select.
    *
    * */
    private fun checkSelectedPlanets():Boolean{
        Constants.planetCounter = 0
        for (planet in Constants.PLANETS){
            if (planet.selectedPos != -1){
                ++Constants.planetCounter
            }
        }
        return Constants.planetCounter<6
    }

    fun setOnChangeListener(onChangeListener: OnChangeListener){
        this.onChangeListener = onChangeListener
    }

    interface OnChangeListener{
        fun onChange()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    private class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvDestinationCount : TextView = itemView.findViewById(R.id.tvDestinationCount)
        var planets : ImageView = itemView.findViewById(R.id.ibPlanet)
        val vehicles : RecyclerView = itemView.findViewById(R.id.rvVehicles)
    }

}