package com.example.domain.usecase.impl

import com.example.core.DataResult
import com.example.core.UseCaseResult
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UpdateUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 10/08/21.
 */
class UpdateUserUseCaseImpl(
	private val userRepository: UserRepository
) : UpdateUserUseCase {

	override fun invoke(
		name: String?,
		city: String?,
		bio: String?,
	): Flow<UseCaseResult<User, Throwable>> {
		return userRepository.updateUser(name, city, bio).map { result ->
			when (result) {
				is DataResult.Success -> UseCaseResult.Success(result.data)
				is DataResult.Loading -> UseCaseResult.Loading
				is DataResult.Failed -> UseCaseResult.Failed(result.error)
			}
		}
	}
}