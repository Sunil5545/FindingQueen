package com.example.findingqueen.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


// TODO: Why has here been specifically added Planet List Response.

data class Planets(
    @SerializedName("name")
    val name : String,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("selectedPos")
    var selectedPos: Int = -1
): Serializable

data class Vehicles(
    @SerializedName("name")
    val name: String,
    @SerializedName("total_no")
    var totalNum: Int,
    @SerializedName("max_distance")
    val maxDistance: Int,
    @SerializedName("speed")
    val speed: Int
) : Serializable

data class TokenResponse(
    @SerializedName("token")
    val token: String
)

data class FindFalconeResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("planet_name")
    val planetName: String,
    @SerializedName("error")
    val error: String
)
