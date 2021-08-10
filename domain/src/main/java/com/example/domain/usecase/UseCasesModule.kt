package com.example.domain.usecase

import com.example.domain.repository.FoodRepository
import com.example.domain.usecase.impl.GetAllFoodItemsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
@Module
@InstallIn(ViewModelComponent::class)
internal object UseCasesModule {
	@Provides
	fun getAllFoodItems(
		foodRepository: FoodRepository
	): GetAllFoodItemsUseCase = GetAllFoodItemsUseCaseImpl(foodRepository)
}