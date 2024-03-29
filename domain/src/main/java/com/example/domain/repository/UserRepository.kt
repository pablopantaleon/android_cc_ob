package com.example.domain.repository

import com.example.domain.entity.Food
import com.example.domain.entity.LikedTransaction
import com.example.domain.entity.User
import com.example.core.DataResult
import com.example.core.Repository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
interface UserRepository : Repository {

	/**
	 * Log In using the provided credentials
	 * @return User
	 */
	fun logInWithCredentials(
		email: String,
		password: String,
	): Flow<DataResult<User>>

	/**
	 * Log out the current logged in user
	 */
	fun logOut(): Flow<DataResult<Unit>>

	/**
	 * Check if user is logged in or not
	 * @return true = user is logged in; false otherwise
	 */
	fun isUserLoggedIn(): Flow<DataResult<Boolean>>

	/**
	 * Get user object
	 * @return User
	 */
	fun getUser(): Flow<DataResult<User>>

	/**
	 * Update user information
	 * Note: None of the parameters are required
	 *
	 * @param name: (Optional) Value associated to the user that identifies it
	 * @param city: (Optional) City where the user has born
	 * @param bio: (Optional) Short description of the user's biography
	 * @return updated User
	 */
	fun updateUser(
		name: String? = null,
		city: String? = null,
		bio: String? = null,
	): Flow<DataResult<User>>

	/**
	 * Add/remove [Food] to favorites liked array
	 * @return true if success; false otherwise
	 */
	fun updateFoodLikedState(foodId: String, isLiked: Boolean): Flow<DataResult<LikedTransaction>>
}