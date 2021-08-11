package com.example.domain.usecase

import com.example.domain.repository.FoodRepository
import com.example.domain.usecase.impl.GetAllFoodItemsUseCaseImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
class GetAllFoodItemsUseCaseTest {

	private lateinit var useCase: GetAllFoodItemsUseCase
	private lateinit var foodRepository: FoodRepository

	@Before
	fun setUp() {
		foodRepository = mock(FoodRepository::class.java)
		useCase = GetAllFoodItemsUseCaseImpl(foodRepository)
	}
}