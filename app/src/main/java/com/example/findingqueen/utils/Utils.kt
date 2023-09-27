package com.example.findingqueen.utils

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.findingqueen.R

var mProgressDialog: Dialog? = null

fun <F : Fragment, C : Int> FragmentActivity.addRemoveFragment(
    addNewFragment: Boolean,
    fragment: F,
    containerViewID: C,
    addToBackStack: Boolean = false,
    shouldAdd: Boolean = false
) {
    var fragmentTransaction = supportFragmentManager.beginTransaction()

    when {
        shouldAdd -> fragmentTransaction
            .add(containerViewID, fragment)
            .commit()

        addNewFragment -> {
            if (addToBackStack) {
                supportFragmentManager.popBackStack()
                fragmentTransaction.replace(containerViewID, fragment)
                fragmentTransaction.addToBackStack(fragment.toString())
            } else
                fragmentTransaction.replace(containerViewID, fragment)

            fragmentTransaction.commit()
        }

        else ->
            fragmentTransaction
                .remove(fragment)
                .commit()
    }
}


//      Resetting data for next Game Cycle.
fun resetDataForNewGame(){
    Constants.PLANETS = arrayListOf()
    Constants.VEHICLES = arrayListOf()
    Constants.planetCounter = 0
    Constants.TIME_TAKEN = 0
}

fun showProgressDialog(context:Context){
    mProgressDialog = Dialog(context)
    mProgressDialog!!.setContentView(R.layout.dialog_custom_progress)
    mProgressDialog!!.show()
}

fun hideProgressDialog(){
    if (mProgressDialog != null){
        mProgressDialog!!.dismiss()
    }
}

fun isNetworkAvailable(context: Context) : Boolean{
    val connectivityManager = context.
    getSystemService(Context.CONNECTIVITY_SERVICE) as
            ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

        val network = connectivityManager.activeNetwork ?:return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?:return false

        return  when{
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }else{
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

}