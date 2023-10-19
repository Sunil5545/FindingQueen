package com.example.findingqueen.repository

import com.example.findingqueen.service.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun<T> safeApiCall(apiCall:suspend () -> T) : ApiResponse<T>{
        return withContext(Dispatchers.IO){
            try {
                ApiResponse.Success(apiCall.invoke())
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException ->
                        ApiResponse.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    else -> {
                        ApiResponse.Failure(
                            true,
                            404,
                            null
                        )
                    }
                }
            }
        }
    }

}