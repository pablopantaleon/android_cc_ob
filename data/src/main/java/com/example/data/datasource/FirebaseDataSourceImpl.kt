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

private const val FIREBASE_KEY_USERS = "users"
private const val FIREBASE_KEY_ITEMS = "items"

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
			return getUserProfile()
		}

		throw FirebaseAuthInvalidCredentialsException("-1", "Invalid Credentials")
	}

	@Throws(FirebaseAuthException::class)
	override suspend fun getUserProfile(): UserDataModel {
		val firebaseUserId: String? = firebaseAuth.currentUser?.uid

		if (firebaseUserId != null) {
			val firebaseUserProfile = firestore
				.collection(FIREBASE_KEY_USERS)
				.document(firebaseUserId)
				.get().await()
			firebaseUserProfile.toObject(UserDataModel::class.java)?.let {
				return@getUserProfile it
			}
		}
		throw FirebaseAuthException("-1", "User not found")
	}

	override suspend fun observeUserLoggedInState(): Flow<Boolean> {
		return callbackFlow {
			val listener = FirebaseAuth.AuthStateListener { result ->
				sendBlocking(result.currentUser != null)
			}
			firebaseAuth.addAuthStateListener(listener)
			awaitClose { firebaseAuth.removeAuthStateListener(listener) }
		}.buffer(Channel.CONFLATED) // send the latest value if conflicts exists
	}

	override suspend fun getAllFoodItems(): List<FoodDataModel> {
		val result = firestore.collection(FIREBASE_KEY_ITEMS).get().await()
		val items = mutableListOf<FoodDataModel>()
		result.forEach { queryDocumentSnapshot ->
			val value = queryDocumentSnapshot.toObject(FoodDataModel::class.java)
			items.add(value)
		}
		return items
	}

	override suspend fun getFoodItemsByCategoryId(categoryId: String): List<FoodDataModel> {
		TODO("Not yet implemented")
	}
}