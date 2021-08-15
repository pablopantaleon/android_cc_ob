package com.example.domain.usecase.impl

import com.example.domain.entity.User
import com.example.core.DataResult
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetUserUseCase
import com.example.core.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 10/08/21.
 */
class GetUserUseCaseImpl(
	private val userRepository: UserRepository,
) : GetUserUseCase {

	override fun invoke(): Flow<UseCaseResult<User, Throwable>> {
		return userRepository.getUser().map { result ->
			when (result) {
				is DataResult.Success -> UseCaseResult.Success(result.data)
				is DataResult.Loading -> UseCaseResult.Loading
				is DataResult.Failed -> UseCaseResult.Failed(result.error)
			}
		}
	}
}