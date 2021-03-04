package com.rn1.puffren.data

sealed class DataResult<out R> {

    data class Success<out T>(val data: T) : DataResult<T>()
    data class Fail(val error: String) : DataResult<Nothing>()
    data class Error(val exception: Exception) : DataResult<Nothing>()
    object Loading : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[result=$data]"
            is Fail -> "Fail[error=$error]"
            is Error -> "Error[exception=${exception.message}]"
            Loading -> "Loading"
        }
    }

}