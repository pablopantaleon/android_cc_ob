package com.example.domain.usecase

import com.example.domain.entity.LikedTransaction
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
interface UpdateFoodLikedStateUseCase {

	fun invoke(foodId: String, isLiked: Boolean): Flow<UseCaseResult<LikedTransaction, Unit>>
}