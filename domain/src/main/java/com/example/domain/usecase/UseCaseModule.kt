package com.example.domain.usecase

import com.example.domain.repository.FoodRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.impl.GetFoodItemsUseCaseImpl
import com.example.domain.usecase.impl.GetUserUseCaseImpl
import com.example.domain.usecase.impl.IsUserLoggedInUseCaseImpl
import com.example.domain.usecase.impl.LogInWithCredentialsUseCaseImpl
import com.example.domain.usecase.impl.LogOutUseCaseImpl
import com.example.domain.usecase.impl.UpdateFoodLikedStateUseCaseImpl
import com.example.domain.usecase.impl.UpdateUserUseCaseImpl
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
	fun providesFoodItems(
		foodRepository: FoodRepository
	): GetFoodItemsUseCase = GetFoodItemsUseCaseImpl(foodRepository)

	@Provides
	@ViewModelScoped
	fun providesIsUserLoggedIn(
		userRepository: UserRepository,
	): IsUserLoggedInUseCase = IsUserLoggedInUseCaseImpl(userRepository)

	@Provides
	@ViewModelScoped
	fun provideUpdateUser(
		userRepository: UserRepository,
	): UpdateUserUseCase = UpdateUserUseCaseImpl(userRepository)

	@Provides
	@ViewModelScoped
	fun providesLogOut(
		userRepository: UserRepository,
	): LogOutUseCase = LogOutUseCaseImpl(userRepository)

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

	@Provides
	@ViewModelScoped
	fun providesUpdateFoodLikedState(
		userRepository: UserRepository,
	): UpdateFoodLikedStateUseCase = UpdateFoodLikedStateUseCaseImpl(userRepository)
}