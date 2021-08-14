package com.example.androidccforob.login

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 * Data validation state of the login form.
 */
data class LogInFormState(
	val isEmailAddressValid: Boolean = false,
	val isPasswordValid: Boolean = false,
	val requiredFieldsFilled: Boolean = false,
)