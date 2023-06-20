package com.ex.applicationxxx

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {

    lateinit var apiService: RetrofitService

        private val _data = MutableLiveData<String>()
        val data: LiveData<String> get() = _data

    private val _response = MutableLiveData<Apiresponse>()
    val response: LiveData<Apiresponse> get() = _response

    private val dumData = DummyData()
    private val _dummmyList = MutableLiveData<List<LegoFort>>()
    val dummmyList: LiveData<List<LegoFort>> get() = _dummmyList

    init {
        _dummmyList.value = dumData.homeList
    }

        fun fetchData() {
            viewModelScope.launch {
                try {
                    val response = Application.RetrofitClient.myApiService
                    _data.postValue(response.getjoke().value)
                } catch (e: Exception) {
                    // Handle error
                }
            }
        }

    fun fetchMultiData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                apiService = Application.RetrofitClient.myApiService
//                _response.postValue(Apiresponse.Success(apiService.getjoke()))
                withContext(Dispatchers.Main) {
                    _response.postValue(Apiresponse.Success(apiService.getjoke()))
                }
            } catch (e: Exception) {

//                _response.postValue(Apiresponse.Error(e.toString()))
                withContext(Dispatchers.Main) {
                    _response.postValue(Apiresponse.Error(e.toString()))
                }
            }
        }
    }

}