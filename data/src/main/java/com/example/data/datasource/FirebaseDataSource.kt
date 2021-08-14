package com.example.data.datasource

import com.example.data.model.FoodDataModel
import com.example.data.model.UserDataModel
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
interface FirebaseDataSource {

	/**
	 * Log in using user credentials
	 * @param email
	 * @param password
	 * @return [UserDataModel] that represents the user
	 * @throws [FirebaseAuthException] if credentials are invalid
	 */
	@Throws(FirebaseAuthException::class)
	suspend fun logInWithCredentials(email: String, password: String): UserDataModel

	/**
	 * Get user data
	 * @return [UserDataModel] that represents the user
	 * @throws [FirebaseAuthException] if user not found (i.e. user is not logged in)
	 */
	@Throws(FirebaseAuthException::class)
	suspend fun getUser(): UserDataModel

	/**
	 * Update user data
	 * Note: None of the parameters are required
	 *
	 * @param username: (Optional) Value associated to the user that identifies it
	 * @param city: (Optional) City where the user has born
	 * @param bio: (Optional) Short description of the user's biography
	 * @return [UserDataModel] that represents the user
	 * @throws [FirebaseAuthException] if user not found (i.e. user is not logged in)
	 */
	@Throws(FirebaseAuthException::class)
	suspend fun updateUser(
		name: String? = null,
		city: String? = null,
		bio: String? = null,
	): UserDataModel

	/**
	 * Kill current session user
	 */
	suspend fun logOut()

	/**
	 * Observe user session state
	 * @return true if user is logged in; false otherwise
	 */
	suspend fun observeUserLoggedInState(): Flow<Boolean>

	/**
	 * Get all food elements
	 * @return A list of [FoodDataModel]'s
	 */
	suspend fun getFoodItems(): List<FoodDataModel>

	/**
	 * Add/remove food from liked user's array
	 * @param foodId: ID of the [FoodDataModel] element
	 * @param isLiked: New state that indicates if the [FoodDataModel] is liked or not
	 * @throws [Exception] if [UserDataModel] is null or exception occurs
	 */
	@Throws(Exception::class)
	suspend fun updateFoodLikedState(foodId: String, isLiked: Boolean)

}