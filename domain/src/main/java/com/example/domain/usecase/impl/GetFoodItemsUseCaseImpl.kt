package com.example.domain.usecase.impl

import com.example.domain.entity.Food
import com.example.core.DataResult
import com.example.domain.repository.FoodRepository
import com.example.domain.usecase.GetFoodItemsUseCase
import com.example.core.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
class GetFoodItemsUseCaseImpl(
	private val foodRepository: FoodRepository
) : GetFoodItemsUseCase {

	override fun invoke(): Flow<UseCaseResult<List<Food>, Throwable>> {
		return foodRepository.getFoodItems().map { result ->
			when (result) {
				is DataResult.Success -> UseCaseResult.Success(result.data)
				is DataResult.Loading -> UseCaseResult.Loading
				is DataResult.Failed -> UseCaseResult.Failed(result.error)
			}
		}
	}
}