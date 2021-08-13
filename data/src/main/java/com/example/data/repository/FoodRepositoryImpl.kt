package com.example.data.repository

import com.example.data.datasource.FirebaseDataSource
import com.example.data.mapper.EntityMapper
import com.example.domain.entity.Food
import com.example.domain.repository.DataResult
import com.example.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
class FoodRepositoryImpl(
	private val firebaseDataSource: FirebaseDataSource,
	private val entityMapper: EntityMapper,
) : FoodRepository {

	override fun getAllFoodItems(): Flow<DataResult<List<Food>>> {
		return flow {
			emit(DataResult.Loading)
			val result = firebaseDataSource.getAllFoodItems()
			val foodList = entityMapper.toFoodList(result)
			emit(DataResult.Success(foodList))
		}.catch { e ->
			Timber.e(e)
			emit(DataResult.Failed("Unkown Error"))
		}
	}

	override fun getFoodItemsByCategoryId(categoryId: String): Flow<DataResult<List<Food>>> {
		TODO("Not yet implemented")
	}
}