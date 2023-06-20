package com.ex.applicationxxx

sealed class Apiresponse {
    data class Success(val lego: LegoFort) : Apiresponse()
    data class Error(val errorMessage: String) : Apiresponse()
}
