package com.example.androidccforob.app

import android.util.Patterns

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 12/08/21.
 */
class DataValidator {

	/**
	 * A placeholder username validation check
	 */
	fun isEmailValid(username: String): Boolean {
		return if (username.contains("@")) {
			Patterns.EMAIL_ADDRESS.matcher(username).matches()
		} else {
			username.isNotBlank()
		}
	}

	/**
	 * A placeholder password validation check
	 */
	fun isPasswordValid(password: String): Boolean {
		return password.length > 5
	}
}