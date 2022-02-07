package com.kingstek.kurencykonverter.main

import com.kingstek.kurencykonverter.data.CurrencyApi
import com.kingstek.kurencykonverter.data.models.CurrencyResponse
import com.kingstek.kurencykonverter.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
)  : MainRepository {
    override suspend fun getRates(base: String): Resource<CurrencyResponse> {

        return try {

            var response = api.getRates(base)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}