package com.kingstek.kurencykonverter.main

import com.kingstek.kurencykonverter.data.models.CurrencyResponse
import com.kingstek.kurencykonverter.util.Resource

interface MainRepository {

    suspend fun getRates(base: String) : Resource<CurrencyResponse>

}