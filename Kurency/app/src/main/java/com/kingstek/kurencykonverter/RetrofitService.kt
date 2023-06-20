package com.ex.applicationxxx

import retrofit2.http.GET

interface RetrofitService {
    @GET("jokes/random")
    suspend fun getjoke(): LegoFort
}