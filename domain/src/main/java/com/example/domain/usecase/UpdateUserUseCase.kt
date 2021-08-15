package com.example.domain.usecase

import com.example.core.UseCase
import com.example.core.UseCaseResult
import com.example.domain.entity.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
interface UpdateUserUseCase : UseCase {

	fun invoke(name: String?, city: String?, bio: String?): Flow<UseCaseResult<User, Throwable>>
}