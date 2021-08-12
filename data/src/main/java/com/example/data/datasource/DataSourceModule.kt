package com.example.data.datasource

import com.example.data.mapper.EntityMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
class DataSourceModule {

	@Singleton
	@Provides
	fun providesEntityMapper(): EntityMapper = EntityMapper()

	@Singleton
	@Provides
	fun providesFirebaseDataSource(
	): FirebaseDataSource = FirebaseDataSourceImpl(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())

}