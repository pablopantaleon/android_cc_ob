package com.example.data.datasource

import com.example.data.model.FoodDataModel
import com.example.data.model.UserDataModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
interface FirebaseDataSource {

	suspend fun logInWithCredentials(email: String, password: String): UserDataModel

	suspend fun getUserProfile(): UserDataModel

	suspend fun observeUserLoggedInState(): Flow<Boolean>

	suspend fun getFoodItems(): List<FoodDataModel>

}