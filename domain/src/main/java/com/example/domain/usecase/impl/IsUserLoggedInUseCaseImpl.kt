package com.example.domain.usecase.impl

import com.example.core.DataResult
import com.example.core.UseCaseResult
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.IsUserLoggedInUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 10/08/21.
 */
class IsUserLoggedInUseCaseImpl @Inject constructor(
	private val userRepository: UserRepository,
) : IsUserLoggedInUseCase {

	override fun invoke(): Flow<UseCaseResult<Boolean, Throwable>> {
		return userRepository.isUserLoggedIn().map { result ->
			when (result) {
				is DataResult.Success -> UseCaseResult.Success(result.data)
				is DataResult.Loading -> UseCaseResult.Loading
				is DataResult.Failed -> UseCaseResult.Failed(result.error)
			}
		}
	}
}