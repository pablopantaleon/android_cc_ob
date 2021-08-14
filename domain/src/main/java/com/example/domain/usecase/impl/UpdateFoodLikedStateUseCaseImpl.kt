package com.example.domain.usecase.impl

import com.example.domain.entity.LikedTransaction
import com.example.domain.repository.DataResult
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UpdateFoodLikedStateUseCase
import com.example.domain.usecase.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 10/08/21.
 */
class UpdateFoodLikedStateUseCaseImpl(
	private val userRepository: UserRepository,
) : UpdateFoodLikedStateUseCase {

	override fun invoke(foodId: String, isLiked: Boolean): Flow<UseCaseResult<LikedTransaction, Unit>> {
		return userRepository.updateFoodLikedState(foodId, isLiked).map { result ->
			when (result) {
				is DataResult.Success -> UseCaseResult.Succeed(result.data)
				is DataResult.Loading -> UseCaseResult.Loading
				is DataResult.Failed -> UseCaseResult.Failed(Unit)
			}
		}
	}
}