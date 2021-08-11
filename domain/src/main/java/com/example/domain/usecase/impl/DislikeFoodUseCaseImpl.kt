package com.example.domain.usecase.impl

import com.example.domain.entity.User
import com.example.domain.repository.DataResult
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.DislikeFoodUseCase
import com.example.domain.usecase.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 10/08/21.
 */
class DislikeFoodUseCaseImpl(
	private val userRepository: UserRepository
) : DislikeFoodUseCase {

	override fun invoke(foodId: String): Flow<UseCaseResult<User, Unit>> {
		return userRepository.removeFoodFromFavorites().map { result ->
			when (result) {
				is DataResult.Success -> UseCaseResult.Succeed(result.data)
				is DataResult.Loading -> UseCaseResult.Loading
				else -> UseCaseResult.Failed(Unit)
			}
		}
	}
}