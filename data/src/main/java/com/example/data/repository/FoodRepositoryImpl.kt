package com.example.data.repository

import com.example.data.datasource.FirebaseDataSource
import com.example.domain.entity.Food
import com.example.domain.repository.DataResult
import com.example.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
class FoodRepositoryImpl(private val firebaseDataSource: FirebaseDataSource) : FoodRepository {

	override fun getAllFoodItems(): Flow<DataResult<List<Food>>> {
		TODO("Not yet implemented")
	}

	override fun getFoodItemsByCategoryId(categoryId: String): Flow<DataResult<List<Food>>> {
		TODO("Not yet implemented")
	}
}