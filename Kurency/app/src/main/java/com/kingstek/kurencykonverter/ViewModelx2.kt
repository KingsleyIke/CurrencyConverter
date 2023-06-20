package com.ex.applicationxxx

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModelx2(private val repository: MyRepository) : ViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data

    private val _response = MutableLiveData<Apiresponse>()
    val response: LiveData<Apiresponse> get() = _response

    fun fetchData() {
        viewModelScope.launch {
            try {
                val result = repository.fetchData()
                _data.postValue(result)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun fetchMultiData() {
        viewModelScope.launch {
            try {
                val result = repository.fetchMultiData()
                _response.postValue(result)
            } catch (e: Exception) {
                _response.postValue(Apiresponse.Error(e.toString()))
            }
        }
    }
}
