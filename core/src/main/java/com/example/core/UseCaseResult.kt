package com.example.core

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
sealed class UseCaseResult<out Type, out Failure> {
	data class Success<Type>(val data: Type) : UseCaseResult<Type, Nothing>()
	data class Failed<Failure>(val error: Failure) : UseCaseResult<Nothing, Failure>()
	object Loading : UseCaseResult<Nothing, Nothing>()
	object Initial : UseCaseResult<Nothing, Nothing>()
}
