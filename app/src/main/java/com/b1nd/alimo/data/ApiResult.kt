package com.b1nd.alimo.data

sealed class ApiResult<T>(val data:T?=null, val error: Throwable?=null){
    class Success<T>(data: T):ApiResult<T>(data = data)
    class Error<T>(error: Throwable):ApiResult<T>(error = error)
    class Loading<T>:ApiResult<T>()
}