package com.example.findingqueen.service

import okhttp3.ResponseBody

sealed class ApiResponse<out T> {

    data class Success<out T>(val value: T): ApiResponse<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : ApiResponse<Nothing>()

    object Loading : ApiResponse<Nothing>()
}