package com.example.domain.usecase

import java.lang.Exception
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
interface IsUserLoggedInUseCase {

	fun invoke(): Flow<UseCaseResult<Boolean, Exception>>
}