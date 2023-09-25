package com.example.findingqueen.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findingqueen.R
import com.example.findingqueen.models.Vehicles
import com.example.findingqueen.utils.Constants


class VehicleListAdapter (
    private val context: Context,
    private val list: ArrayList<Vehicles>,
    private var mDestinationPos: Int = 0
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var selectedVehicle = -1
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.item_vehicle
                    ,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MyViewHolder){

/*
* Conditions to disable vehicle buttons.
* Vehicle_Count < 0
* Vehicle max_distance >= planet_distance
* Future Scope -> Auto-Select and hint user most useful vehicle with distance and Speed.
*
* */
            if (Constants.VEHICLES[position].totalNum == 0
                || Constants.VEHICLES[position].maxDistance < (Constants.PLANETS[mDestinationPos].distance)){

                holder.vehicleName.isEnabled = false
                holder.vehicleCount.alpha = 0.5f

            }else{
                if (Constants.PLANETS[mDestinationPos].selectedPos == -1 && Constants.planetCounter >=4){
                    holder.vehicleName.isEnabled = false
                    holder.vehicleCount.alpha = 0.5f
                }else{
                    holder.vehicleName.isEnabled = true
                }

            }



/* It retrieves any pre-selected vehicles and
*  when user is moving through selection on same page it updates the selections.
* */
            if (selectedVehicle == -1 && Constants.PLANETS[mDestinationPos].selectedPos == holder.adapterPosition){
                selectedVehicle = holder.adapterPosition
                holder.vehicleName.isChecked = true
            }else{
                holder.vehicleName.isChecked = holder.adapterPosition == selectedVehicle
            }

// It sets the Vehicle name and Vehicle counts
            holder.vehicleName.text = Constants.VEHICLES[position].name
            holder.vehicleCount.text = "("+Constants.VEHICLES[position].totalNum+")"

/*
* It does selections and Deselection of Vehicles on the fly as it notify on Item change.
* Also sends callBack to Destination Adapter to Refresh view to update Vehicle Counts in
* other Destination Planet View.
* */
            holder.vehicleName.setOnClickListener {
                if (selectedVehicle != holder.adapterPosition){
                    if (selectedVehicle != -1){
                        addRemoveVehicle(true,selectedVehicle)
                        notifyItemChanged(selectedVehicle)
                    }
                    selectedVehicle = holder.adapterPosition
                    addRemoveVehicle(false,holder.adapterPosition)
                    notifyItemChanged(position)
                }
                Constants.PLANETS[mDestinationPos].selectedPos = position
                onClickListener?.onClick(position)
            }

        }
    }


    /* This is implementation to add and remove Vehicle Count
    when user tap on the vehicles to select.
    */
    private fun addRemoveVehicle(add: Boolean,position:Int){
        if (!add){
            --Constants.VEHICLES[position].totalNum
            Constants.TIME_TAKEN = Constants.TIME_TAKEN + Constants.PLANETS[mDestinationPos].distance/Constants.VEHICLES[position].speed
        }else{
            ++Constants.VEHICLES[position].totalNum
            Constants.TIME_TAKEN = Constants.TIME_TAKEN - Constants.PLANETS[mDestinationPos].distance/Constants.VEHICLES[position].speed
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val vehicleName: RadioButton = itemView.findViewById(R.id.rbVehicleName)
        val vehicleCount: TextView = itemView.findViewById(R.id.tvVehicleCount)
    }
}