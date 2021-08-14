package com.example.domain.usecase

import com.example.domain.repository.FoodRepository
import com.example.domain.usecase.impl.GetFoodItemsUseCaseImpl
import org.junit.Before
import org.mockito.Mockito.mock

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
class GetFoodItemsUseCaseTest {

	private lateinit var useCase: GetFoodItemsUseCase
	private lateinit var foodRepository: FoodRepository

	@Before
	fun setUp() {
		foodRepository = mock(FoodRepository::class.java)
		useCase = GetFoodItemsUseCaseImpl(foodRepository)
	}
}