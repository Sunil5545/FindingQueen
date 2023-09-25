package com.example.findingqueen.service

import com.example.findingqueen.models.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.http.*

interface ApiInterface {

    @GET("planets")
    suspend fun getPlanets(): ArrayList<Planets>

    @GET("vehicles")
    suspend fun getVehicles(): ArrayList<Vehicles>

    @POST("token")
    suspend fun getToken(): TokenResponse

    @POST("find")
    suspend fun findFalcone(
        @Body params: RequestBody
    ): FindFalconeResponse

}