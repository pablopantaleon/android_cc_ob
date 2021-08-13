package com.example.domain.usecase

import com.example.domain.repository.FoodRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.impl.GetAllFoodItemsUseCaseImpl
import com.example.domain.usecase.impl.GetFoodItemsByCategoryUseCaseImpl
import com.example.domain.usecase.impl.GetUserUseCaseImpl
import com.example.domain.usecase.impl.IsUserLoggedInUseCaseImpl
import com.example.domain.usecase.impl.LogInWithCredentialsUseCaseImpl
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
	@ViewModelScoped
	fun providesAllFoodItems(
		foodRepository: FoodRepository
	): GetAllFoodItemsUseCase = GetAllFoodItemsUseCaseImpl(foodRepository)

	@Provides
	@ViewModelScoped
	fun providesFoodItemsByCategoryId(
		foodRepository: FoodRepository
	): GetFoodItemsByCategoryUseCase = GetFoodItemsByCategoryUseCaseImpl(foodRepository)

	@Provides
	@ViewModelScoped
	fun providesIsUserLoggedIn(
		userRepository: UserRepository,
	): IsUserLoggedInUseCase = IsUserLoggedInUseCaseImpl(userRepository)

	@Provides
	@ViewModelScoped
	fun providesLogInWithCredentials(
		userRepository: UserRepository,
	): LogInWithCredentialsUseCase = LogInWithCredentialsUseCaseImpl(userRepository)

	@Provides
	@ViewModelScoped
	fun providesUser(
		userRepository: UserRepository,
	): GetUserUseCase = GetUserUseCaseImpl(userRepository)
}