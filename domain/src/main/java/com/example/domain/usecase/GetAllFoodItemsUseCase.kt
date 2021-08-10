package com.example.domain.usecase

import com.example.domain.entity.Food
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
interface GetAllFoodItemsUseCase {

	fun invoke(): Flow<UseCaseResult<List<Food>, Unit>>
}