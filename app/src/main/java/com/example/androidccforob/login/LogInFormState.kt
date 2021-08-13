package com.example.androidccforob.login

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 * Data validation state of the login form.
 */
data class LoginFormState(
	val usernameError: Boolean = false,
	val passwordError: Boolean = false,
	val hasValidData: Boolean? = null,
)