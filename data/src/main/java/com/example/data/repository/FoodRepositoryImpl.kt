package com.example.data.repository

import com.example.core.DataResult
import com.example.data.datasource.FirebaseDataSource
import com.example.data.mapper.EntityMapper
import com.example.data.model.FoodDataModel
import com.example.domain.entity.Food
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

	override fun getFoodItems(): Flow<DataResult<List<Food>>> {
		return flow {
			emit(DataResult.Loading)
			val foods = firebaseDataSource.getFoodItems()
			val user = firebaseDataSource.getUser()
			val foodList = entityMapper.toFoodList(foods, user)
			emit(DataResult.Success(foodList))
		}.catch { e ->
			Timber.e(e)
			emit(DataResult.Failed(e))
			doSomethingWithFlow()
		}
	}

	fun doSomethingWithFlow(): Flow<Result<List<FoodDataModel>>> {
		return flow {
			emit(Result.Loading)
			val result = firebaseDataSource.getFoodItems()
			emit(Result.Success(result))
		}.catch { error ->
			Timber.e(error)
			emit(Result.Failed(error))
		}
	}
}