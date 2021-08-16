package com.example.data.di

import com.example.data.datasource.FirebaseDataSource
import com.example.data.mapper.EntityMapper
import com.example.data.repository.FoodRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.FoodRepository
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {

	@Singleton
	@Provides
	fun providesUserRepository(
		firebaseDataSource: FirebaseDataSource,
		entityMapper: EntityMapper,
	): UserRepository = UserRepositoryImpl(firebaseDataSource, entityMapper)

	@Singleton
	@Provides
	fun providesFoodRepository(
		firebaseDataSource: FirebaseDataSource,
		entityMapper: EntityMapper,
	): FoodRepository = FoodRepositoryImpl(firebaseDataSource, entityMapper)
}