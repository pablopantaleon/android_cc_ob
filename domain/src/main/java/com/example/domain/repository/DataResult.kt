package com.example.domain.repository

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
sealed class DataResult<out T> {
	object Loading : DataResult<Nothing>()
	data class Success<out T>(val data: T) : DataResult<T>()
	data class Failed(val message: String) : DataResult<Nothing>()
	object UnknownFailure : DataResult<Nothing>()
}
