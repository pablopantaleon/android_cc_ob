package com.example.data.datasource

import com.example.data.model.UserDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val FIREBASE_KEY_USERS = "users"

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
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
			firebaseUserProfile.toObject(UserDataModel::class.java)
		}
		throw FirebaseAuthException("-1", "User not found")
	}
}