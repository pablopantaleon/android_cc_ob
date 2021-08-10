package com.example.domain.usecase.impl

import com.example.domain.entity.Food
import com.example.domain.repository.DataResult
import com.example.domain.repository.FoodRepository
import com.example.domain.usecase.GetAllFoodItemsUseCase
import com.example.domain.usecase.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
class GetAllFoodItemsUseCaseImpl(
	private val foodRepository: FoodRepository
) : GetAllFoodItemsUseCase {

	override fun invoke(): Flow<UseCaseResult<List<Food>, Unit>> {
		return foodRepository.getAllFoodItems().map { result ->
			when (result) {
				is DataResult.Success -> UseCaseResult.Succeed(result.data)
				is DataResult.Loading -> UseCaseResult.Loading
				else -> UseCaseResult.Failed(Unit)
			}
		}
	}
}