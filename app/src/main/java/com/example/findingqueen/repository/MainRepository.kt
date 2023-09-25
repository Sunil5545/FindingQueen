package com.example.findingqueen.repository

import com.example.findingqueen.service.ApiInterface
import okhttp3.RequestBody
import org.json.JSONObject

class MainRepository(private val apiInterface: ApiInterface) : BaseRepository(){

    suspend fun getPlanetsApi() =
        safeApiCall {
            apiInterface.getPlanets()
        }

    suspend fun getVehiclesApi() =
        safeApiCall {
            apiInterface.getVehicles()
        }

    suspend fun getTokenApi() =
        safeApiCall {
            apiInterface.getToken()
        }

    suspend fun findFalconeApi(params: RequestBody) =
        safeApiCall {
            apiInterface.findFalcone(params)
        }
}