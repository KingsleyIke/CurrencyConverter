package com.ex.applicationxxx

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Application {
//    val retrofit = Retrofit.Builder()
//        .baseUrl("https://api.chucknorris.io") // Replace with your API URL
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val apiService = retrofit.create(Retrofit::class.java)

    object RetrofitClient {
        private const val BASE_URL = "https://api.legofort.io/"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val myApiService: RetrofitService by lazy {
            retrofit.create(RetrofitService::class.java)
        }
    }


}