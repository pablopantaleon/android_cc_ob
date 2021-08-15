package com.example.domain.usecase

import com.example.core.UseCase
import com.example.domain.entity.User
import com.example.core.UseCaseResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
interface GetUserUseCase : UseCase {

	fun invoke(): Flow<UseCaseResult<User, Throwable>>
}