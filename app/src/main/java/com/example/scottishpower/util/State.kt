package com.example.scottishpower.util

sealed class State<out T> {
    class Success<T>(val contents: T): State<T>()
    class Error(val message: String? = null): State<Nothing>()
    data object Loading : State<Nothing>()
}