package com.wipro.interview.wiprocleanarchitecture.domain.utils

sealed class NetworkResult<T>(val data: T? = null, val message: String = "") {
    data class Success<T>(val mData: T) : NetworkResult<T>(data = mData)
    data class Error<T>(val mMessage: String) : NetworkResult<T>(message = mMessage)
    class Loading<T>() : NetworkResult<T>()
}