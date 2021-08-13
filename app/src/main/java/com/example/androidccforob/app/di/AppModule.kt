package com.example.androidccforob.app.di

import com.example.androidccforob.app.DataValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 12/08/21.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

	@Singleton
	@Provides
	fun providesDataValidator() = DataValidator()
}