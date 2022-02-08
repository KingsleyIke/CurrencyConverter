package com.kingstek.kurencykonverter.data

import com.kingstek.kurencykonverter.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/latest?access_key=5087820c13d18c2f0f6c8662aa53bded")
    suspend fun getRates (
        @Query("base") base: String
    ) : Response<CurrencyResponse>
}