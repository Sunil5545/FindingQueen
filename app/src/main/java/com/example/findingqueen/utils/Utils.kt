package com.example.findingqueen.utils

import android.app.Dialog
import android.content.Context
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