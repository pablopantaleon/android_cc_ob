package com.example.domain.usecase

import app.cash.turbine.test
import com.example.core.DataResult
import com.example.core.UseCaseResult
import com.example.domain.entity.Food
import com.example.domain.repository.FoodRepository
import com.example.domain.usecase.impl.GetFoodItemsUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
@ExperimentalTime
class GetFoodItemsUseCaseTest {

	private lateinit var useCase: GetFoodItemsUseCase
	private val error = Throwable()
	private val repository: FoodRepository = mockk()
	private val states = flow {
		emit(DataResult.Loading)
		emit(DataResult.Success(emptyList<Food>()))
		emit(DataResult.Failed(error))
	}

	@Before
	fun setUp() {
		every { repository.getFoodItems() } returns states
		useCase = GetFoodItemsUseCaseImpl(repository)
	}

	@Test
	fun `should transform DataResult to UseCaseResult`() = runBlocking {
		useCase.invoke().test {
			assertEquals(UseCaseResult.Loading, awaitItem())
			assertEquals(UseCaseResult.Success(emptyList<Food>()), awaitItem())
			assertEquals(UseCaseResult.Failed(error), awaitItem())
			awaitComplete()
		}
	}
}