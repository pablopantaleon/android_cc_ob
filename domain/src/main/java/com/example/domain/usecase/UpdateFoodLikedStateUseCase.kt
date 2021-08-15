package com.example.domain.usecase

import com.example.core.UseCase
import com.example.core.UseCaseResult
import com.example.domain.entity.LikedTransaction
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
interface UpdateFoodLikedStateUseCase : UseCase {

	fun invoke(foodId: String, isLiked: Boolean): Flow<UseCaseResult<LikedTransaction, Throwable>>
}