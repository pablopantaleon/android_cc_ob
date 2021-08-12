package com.example.domain.usecase

import com.example.domain.repository.FoodRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.impl.GetAllFoodItemsUseCaseImpl
import com.example.domain.usecase.impl.IsUserLoggedInUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

	@Provides
	fun providesAllFoodItems(
		foodRepository: FoodRepository
	): GetAllFoodItemsUseCase = GetAllFoodItemsUseCaseImpl(foodRepository)

	@Provides
	@ViewModelScoped
	fun providesIsUserLoggedIn(
		userRepository: UserRepository,
	): IsUserLoggedInUseCase = IsUserLoggedInUseCaseImpl(userRepository)
}