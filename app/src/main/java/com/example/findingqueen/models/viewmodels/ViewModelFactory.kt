package com.example.findingqueen.models.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.findingqueen.repository.BaseRepository
import com.example.findingqueen.repository.MainRepository
import java.lang.IllegalArgumentException

class ViewModelFactory (private val repository: BaseRepository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                repository as MainRepository
            ) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}