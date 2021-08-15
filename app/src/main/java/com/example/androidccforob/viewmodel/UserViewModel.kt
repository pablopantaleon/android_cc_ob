package com.example.androidccforob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidccforob.app.DataValidator
import com.example.androidccforob.login.LogInFormState
import com.example.core.UseCaseResult
import com.example.domain.entity.User
import com.example.domain.usecase.GetUserUseCase
import com.example.domain.usecase.IsUserLoggedInUseCase
import com.example.domain.usecase.LogInWithCredentialsUseCase
import com.example.domain.usecase.LogOutUseCase
import com.example.domain.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class UserViewModel @Inject constructor(
	isUserLoggedInUseCase: IsUserLoggedInUseCase,
	getUserUseCase: GetUserUseCase,
	private val logOutUseCase: LogOutUseCase,
	private val logInWithCredentialsUseCase: LogInWithCredentialsUseCase,
	private val updateUserUseCase: UpdateUserUseCase,
	private val dataValidator: DataValidator,
) : ViewModel() {

	/**
	 * ## Immutable Flow States
	 * Helps to listen about new states, subscribe using the View
	 */
	val isLoggedInState = isUserLoggedInUseCase.invoke().stateIn(
		viewModelScope,
		WhileSubscribed(500),
		UseCaseResult.Initial
	)
	val userState = getUserUseCase.invoke().stateIn(
		viewModelScope,
		WhileSubscribed(500),
		UseCaseResult.Loading
	)

	/**
	 * ## Mutable/Immutable Flow States
	 * These flows help to communicate ViewModel actions to subscribed Views
	 */
	private val _logInState =
		MutableStateFlow<UseCaseResult<User, Throwable>>(UseCaseResult.Initial)
	val logInState: StateFlow<UseCaseResult<User, Throwable>> = _logInState
	private val _logInFormValidationState = MutableStateFlow(LogInFormState())
	val logInFormValidationState: StateFlow<LogInFormState> = _logInFormValidationState
	private val _updateUserState =
		MutableStateFlow<UseCaseResult<User, Throwable>>(UseCaseResult.Initial)
	val updateUserState: StateFlow<UseCaseResult<User, Throwable>> = _updateUserState

	/**
	 * Log In using credentials. If success [isLoggedInState] is updated
	 * @param email
	 * @param password
	 */
	fun logIn(email: String, password: String) {
		viewModelScope.launch {
			logInWithCredentialsUseCase.invoke(email, password).collect { result ->
				_logInState.value = result
			}
		}
	}

	/**
	 * Validate if the "email and password" provided meets the requirements
	 * The validation updates the [logInFormValidationState]
	 * @param email
	 * @param password
	 */
	fun onLogInDataChanged(email: String, password: String) {
		_logInFormValidationState.value = LogInFormState(
			isEmailAddressValid = !dataValidator.isEmailValid(email),
			isPasswordValid = !dataValidator.isPasswordValid(password),
			requiredFieldsFilled = email.isNotBlank() && password.isNotBlank(),
		)
	}

	fun updateUser(name: String?, city: String?, bio: String) {
		viewModelScope.launch {
			updateUserUseCase.invoke(name, city, bio).collect { result ->
				_updateUserState.value = result
			}
		}
	}

	fun logOut() {
		viewModelScope.launch {
			logOutUseCase.invoke().collect { }
		}
	}
}