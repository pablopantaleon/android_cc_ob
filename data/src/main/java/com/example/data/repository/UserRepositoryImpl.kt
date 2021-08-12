package com.example.data.repository

import com.example.data.datasource.FirebaseDataSource
import com.example.data.mapper.EntityMapper
import com.example.domain.entity.User
import com.example.domain.repository.DataResult
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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
		}.catch { error ->
			emit(DataResult.Failed(error.message ?: ""))
		}.flowOn(Dispatchers.IO)
	}

	override fun logOut(): Flow<DataResult<Unit>> {
		TODO("Not yet implemented")
	}

	override fun isUserLoggedIn(): Flow<DataResult<Boolean>> {
		return flow {
			emit(DataResult.Loading)
			val result = firebaseDataSource.isUserLoggedIn()
			emit(DataResult.Success(result))
		}.catch {
			emit(DataResult.Success(false))
		}.flowOn(Dispatchers.IO)
	}

	override fun getUser(): Flow<DataResult<User>> {
		TODO("Not yet implemented")
	}

	override fun updateUser(
		username: String?,
		city: String?,
		bio: String?
	): Flow<DataResult<User>> {
		TODO("Not yet implemented")
	}

	override fun addFoodToFavorites(): Flow<DataResult<User>> {
		TODO("Not yet implemented")
	}

	override fun removeFoodFromFavorites(): Flow<DataResult<User>> {
		TODO("Not yet implemented")
	}

	override fun isFoodLiked(): Flow<DataResult<Boolean>> {
		TODO("Not yet implemented")
	}
}