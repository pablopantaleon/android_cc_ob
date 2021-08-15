package com.example.domain.usecase.impl

import com.example.core.DataResult
import com.example.core.UseCaseResult
import com.example.domain.entity.LikedTransaction
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UpdateFoodLikedStateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 10/08/21.
 */
class UpdateFoodLikedStateUseCaseImpl(
	private val userRepository: UserRepository,
) : UpdateFoodLikedStateUseCase {

	override fun invoke(
		foodId: String,
		isLiked: Boolean
	): Flow<UseCaseResult<LikedTransaction, Throwable>> {
		return userRepository.updateFoodLikedState(foodId, isLiked).map { result ->
			when (result) {
				is DataResult.Success -> UseCaseResult.Success(result.data)
				is DataResult.Loading -> UseCaseResult.Loading
				is DataResult.Failed -> UseCaseResult.Failed(result.error)
			}
		}
	}
}