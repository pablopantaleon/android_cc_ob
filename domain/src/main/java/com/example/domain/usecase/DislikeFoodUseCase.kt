package com.example.domain.usecase

import com.example.domain.entity.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
interface DislikeFoodUseCase {

	fun invoke(foodId: String): Flow<UseCaseResult<User, Unit>>
}