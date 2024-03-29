package com.example.domain.usecase

import com.example.core.UseCase
import com.example.core.UseCaseResult
import com.example.domain.entity.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
interface LogInWithCredentialsUseCase : UseCase {

	fun invoke(email: String, password: String): Flow<UseCaseResult<User, Throwable>>
}