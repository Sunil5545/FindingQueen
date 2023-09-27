package com.example.findingqueen.service

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofitClient {

    companion object{

        private const val BASE_URL = "https://findfalcone.geektrust.com/"

        var apiInterface: ApiInterface? = null
// This is retrofit instance
        fun getInstance(): ApiInterface {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder().apply {
                        header("Accept","application/json")
                        addHeader("Content-Type","application/json")
                    }.build()
                    )
                }
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build()

            apiInterface = retrofit.create(ApiInterface::class.java)
            return apiInterface!!
        }

    }

}