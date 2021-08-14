package com.example.data.datasource

import com.example.data.model.FoodDataModel
import com.example.data.model.UserDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber

private const val FIREBASE_KEY_USERS = "users"
private const val FIREBASE_KEY_ITEMS = "items"
private const val FIREBASE_KEY_LIKES = "likes"

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
@ExperimentalCoroutinesApi
class FirebaseDataSourceImpl(
	private val firebaseAuth: FirebaseAuth,
	private val firestore: FirebaseFirestore,
) : FirebaseDataSource {

	@Throws(FirebaseAuthException::class)
	override suspend fun logInWithCredentials(email: String, password: String): UserDataModel {
		val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

		if (result.user != null) {
			return getUser()
		}

		Timber.e("Error while trying to login with credentials using: $email")
		throw FirebaseAuthInvalidCredentialsException("-1", "Invalid Credentials")
	}

	@Throws(FirebaseAuthException::class)
	override suspend fun getUser(): UserDataModel {
		val firebaseUserId: String? = firebaseAuth.currentUser?.uid

		if (firebaseUserId != null) {
			val firebaseUserProfile = firestore
				.collection(FIREBASE_KEY_USERS)
				.document(firebaseUserId)
				.get().await()
			firebaseUserProfile.toObject(UserDataModel::class.java)?.let {
				return@getUser it
			}
		}

		Timber.e("Error while trying to get \"User Profile\"; error=User Not Found")
		throw FirebaseAuthException("-1", "User not found")
	}

	override suspend fun observeUserLoggedInState(): Flow<Boolean> {
		return callbackFlow {
			val listener = FirebaseAuth.AuthStateListener { result ->
				val isUserLoggedIn: Boolean = result.currentUser != null
				Timber.d("[SessionState] Is user logged in? $isUserLoggedIn")
				sendBlocking(isUserLoggedIn)
			}
			firebaseAuth.addAuthStateListener(listener)
			awaitClose { firebaseAuth.removeAuthStateListener(listener) }
		}.buffer(Channel.CONFLATED) // send the latest value if conflicts exists
	}

	override suspend fun getFoodItems(): List<FoodDataModel> {
		val result = firestore.collection(FIREBASE_KEY_ITEMS).get().await()
		val items = mutableListOf<FoodDataModel>()
		result.forEach { queryDocumentSnapshot ->
			val value = queryDocumentSnapshot.toObject(FoodDataModel::class.java)
			items.add(value)
		}
		return items
	}

	override suspend fun updateFoodLikedState(foodId: String, isLiked: Boolean) {
		val user = getUser()
		val query = firestore.collection(FIREBASE_KEY_USERS).document(user.id)
		val likes = user.likes?.toMutableList() ?: mutableListOf()

		// Add/remove food liked state to the array
		if (likes.contains(foodId)) {
			likes.remove(foodId)
		} else {
			likes.add(foodId)
		}
		val updateBundle = user.run { copy(likes = likes) }

		// Use set to create the "likes" field if it doesn't exist
		// and if it exists then use "update" to add/remove liked foodId to the array
		if (user.likes == null) {
			query.set(updateBundle)
		} else {
			query.update(FIREBASE_KEY_LIKES, likes)
		}
	}
}