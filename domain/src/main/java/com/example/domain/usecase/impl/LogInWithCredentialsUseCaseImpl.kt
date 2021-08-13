package com.example.domain.usecase.impl

import com.example.domain.entity.User
import com.example.domain.repository.DataResult
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.LogInWithCredentialsUseCase
import com.example.domain.usecase.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 10/08/21.
 */
class LogInWithCredentialsUseCaseImpl(
	private val userRepository: UserRepository,
) : LogInWithCredentialsUseCase {

	override fun invoke(email: String, password: String): Flow<UseCaseResult<User, Exception>> {
		return userRepository.logInWithCredentials(email, password).map { result ->
			when (result) {
				is DataResult.Success -> UseCaseResult.Succeed(result.data)
				is DataResult.Loading -> UseCaseResult.Loading
				is DataResult.Failed -> UseCaseResult.Failed(Exception(result.message))
			}
		}
	}
}