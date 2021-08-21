package com.example.data.repository

import com.example.core.DataResult
import com.example.data.datasource.FirebaseDataSource
import com.example.data.mapper.EntityMapper
import com.example.domain.entity.LikedTransaction
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
class UserRepositoryImpl(
	private val firebaseDataSource: FirebaseDataSource,
	private val entityMapper: EntityMapper,
) : UserRepository {

	override fun logInWithCredentials(email: String, password: String): Flow<DataResult<User>> {
		return flow {
			emit(DataResult.Loading)
			val userDataModel = firebaseDataSource.logInWithCredentials(email, password)
			emit(DataResult.Success(entityMapper.toUser(userDataModel)))
		}.catch { e ->
			emit(DataResult.Failed(e))
		}
	}

	override fun logOut(): Flow<DataResult<Unit>> {
		return flow {
			firebaseDataSource.logOut()
		}
	}

	override fun isUserLoggedIn(): Flow<DataResult<Boolean>> {
		return flow {
			firebaseDataSource.observeUserLoggedInState().collect { result ->
				emit(DataResult.Success(result))
			}
		}
	}

	override fun getUser(): Flow<DataResult<User>> {
		return flow {
			emit(DataResult.Loading)
			val result = firebaseDataSource.getUser()
			emit(DataResult.Success(entityMapper.toUser(result)))
		}.catch { e ->
			Timber.e(e)
			emit(DataResult.Failed(e))
		}
	}

	override fun updateUser(
		name: String?,
		city: String?,
		bio: String?
	): Flow<DataResult<User>> {
		return flow {
			emit(DataResult.Loading)
			val result = firebaseDataSource.updateUser(name, city, bio)
			emit(DataResult.Success(entityMapper.toUser(result)))
		}.catch { e ->
			Timber.e(e)
			emit(DataResult.Failed(e))
		}
	}

	override fun updateFoodLikedState(
		foodId: String,
		isLiked: Boolean
	): Flow<DataResult<LikedTransaction>> {
		return flow {
			emit(DataResult.Loading)
			firebaseDataSource.updateFoodLikedState(foodId, isLiked)
			emit(DataResult.Success(LikedTransaction(foodId, isLiked, true)))
			Timber.d("[LikedState] - foodId: $foodId, isLiked: $isLiked")
		}.catch { e ->
			Timber.e(e)
			emit(DataResult.Success(LikedTransaction(foodId, !isLiked, false)))
			Timber.d("[LikedState, Error] - foodId: $foodId, isLiked: $isLiked")
		}
	}
}