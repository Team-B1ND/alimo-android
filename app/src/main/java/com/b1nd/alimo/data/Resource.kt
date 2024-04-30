package com.b1nd.alimo.data

sealed class Resource<T>(val data:T?=null, val error: Throwable?=null){
    class Success<T>(data: T):Resource<T>(data = data)
    class Error<T>(error: Throwable):Resource<T>(error = error)
    class Loading<T>:Resource<T>()
}