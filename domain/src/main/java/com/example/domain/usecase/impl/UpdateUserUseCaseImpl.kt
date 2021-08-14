package com.example.domain.usecase.impl

import com.example.domain.entity.User
import com.example.domain.repository.DataResult
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UpdateUserUseCase
import com.example.domain.usecase.UseCaseResult
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
	): Flow<UseCaseResult<User, Unit>> {
		return userRepository.updateUser(name, city, bio).map { result ->
			when (result) {
				is DataResult.Success -> UseCaseResult.Succeed(result.data)
				is DataResult.Loading -> UseCaseResult.Loading
				else -> UseCaseResult.Failed(Unit)
			}
		}
	}
}