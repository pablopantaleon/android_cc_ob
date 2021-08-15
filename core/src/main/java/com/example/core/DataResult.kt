package com.example.core

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
sealed class DataResult<out T> {
	object Loading : DataResult<Nothing>()
	data class Success<out T>(val data: T) : DataResult<T>()
	data class Failed(val error: Throwable) : DataResult<Nothing>()
}
