package com.example.domain.usecase

import app.cash.turbine.test
import com.example.core.DataResult
import com.example.core.UseCaseResult
import com.example.domain.entity.LikedTransaction
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.impl.UpdateFoodLikedStateUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 14/08/21.
 */
@ExperimentalTime
class UpdateFoodLikedStateUseCaseTest {

	private lateinit var useCase: UpdateFoodLikedStateUseCase
	private val error = Throwable()
	private val foodId: String = "1111111111"
	private val likedTransaction: LikedTransaction = mockk()
	private val repository: UserRepository = mockk()
	private val states = flow {
		emit(DataResult.Loading)
		emit(DataResult.Success(likedTransaction))
		emit(DataResult.Failed(error))
	}

	@Before
	fun setUp() {
		every { repository.updateFoodLikedState(foodId, true) } returns states
		useCase = UpdateFoodLikedStateUseCaseImpl(repository)
	}

	@Test
	fun `should transform DataResult to UseCaseResult`() = runBlocking {
		useCase.invoke(foodId, true).test {
			Assert.assertEquals(UseCaseResult.Loading, awaitItem())
			Assert.assertEquals(UseCaseResult.Success(likedTransaction), awaitItem())
			Assert.assertEquals(UseCaseResult.Failed(error), awaitItem())
			awaitComplete()
		}
	}
}