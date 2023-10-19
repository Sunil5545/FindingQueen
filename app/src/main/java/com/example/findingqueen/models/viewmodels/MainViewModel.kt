package com.example.findingqueen.models.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findingqueen.models.*
import com.example.findingqueen.repository.MainRepository
import com.example.findingqueen.service.ApiResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody

class MainViewModel(private val repository: MainRepository) : ViewModel(){

    private val _planetsResponse: MutableLiveData<ApiResponse<ArrayList<Planets>>> = MutableLiveData()
    val planetsResponse: LiveData<ApiResponse<ArrayList<Planets>>>
    get() = _planetsResponse

    fun planetsResponse() = viewModelScope.launch {
        _planetsResponse.value = ApiResponse.Loading
        _planetsResponse.value = repository.getPlanetsApi()
    }

    private val _vehiclesResponse: MutableLiveData<ApiResponse<ArrayList<Vehicles>>> = MutableLiveData()
    val vehiclesResponse: LiveData<ApiResponse<ArrayList<Vehicles>>>
    get() = _vehiclesResponse

    fun vehiclesResponse() = viewModelScope.launch {
        _vehiclesResponse.value = ApiResponse.Loading
        _vehiclesResponse.value = repository.getVehiclesApi()
    }

    private val _tokenResponse: MutableLiveData<ApiResponse<TokenResponse>> = MutableLiveData()
    val tokenResponse: LiveData<ApiResponse<TokenResponse>>
    get() = _tokenResponse

    fun tokenResponse() = viewModelScope.launch {
        _tokenResponse.value = ApiResponse.Loading
        _tokenResponse.value = repository.getTokenApi()
    }

    private val _findFalconeResponse:MutableLiveData<ApiResponse<FindFalconeResponse>> = MutableLiveData()
    val findFalconeResponse: LiveData<ApiResponse<FindFalconeResponse>>
    get() = _findFalconeResponse

    fun findFalconeResponse(request: HashMap<String,Any>) = viewModelScope.launch {
        val requestJson = Gson().toJson(request)
        val requestBody = requestJson.toRequestBody()
        Log.i("MyFalconeRequest","$requestJson")
        _findFalconeResponse.value = ApiResponse.Loading
        _findFalconeResponse.value = repository.findFalconeApi(requestBody)
    }

}