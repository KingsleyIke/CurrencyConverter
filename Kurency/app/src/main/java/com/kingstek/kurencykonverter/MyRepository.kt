package com.ex.applicationxxx

class MyRepository(private  val apiService: RetrofitService) {

    suspend fun fetchData(): String {
        return apiService.getjoke().value
    }

    suspend fun fetchMultiData(): Apiresponse {
        return try {
            Apiresponse.Success(apiService.getjoke())
        } catch (e: Exception) {
            Apiresponse.Error(e.toString())
        }
    }
}