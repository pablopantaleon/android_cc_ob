package com.example.androidccforob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidccforob.app.DataValidator
import com.example.androidccforob.login.LoginFormState
import com.example.domain.entity.User
import com.example.domain.usecase.GetUserUseCase
import com.example.domain.usecase.IsUserLoggedInUseCase
import com.example.domain.usecase.LogInWithCredentialsUseCase
import com.example.domain.usecase.UseCaseResult
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
	private val dataValidator: DataValidator,
	private val logInWithCredentialsUseCase: LogInWithCredentialsUseCase,
) : ViewModel() {

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
	private val _logInState =
		MutableStateFlow<UseCaseResult<User, Exception>>(UseCaseResult.Initial)
	val logInState: StateFlow<UseCaseResult<User, Exception>> = _logInState
	private val _logInFormValidationState = MutableStateFlow(LoginFormState())
	val logInFormValidationState: StateFlow<LoginFormState> = _logInFormValidationState

	fun logIn(email: String, password: String) {
		viewModelScope.launch {
			logInWithCredentialsUseCase.invoke(email, password).collect { result ->
				_logInState.value = result
			}
		}
	}

	fun loginDataChanged(username: String, password: String) {
		val valid =
			!dataValidator.isEmailValid(username) && !dataValidator.isPasswordValid(password)
		_logInFormValidationState.value = LoginFormState(
			usernameError = !dataValidator.isEmailValid(username),
			passwordError = !dataValidator.isPasswordValid(password),
			hasValidData = valid
		)
	}
}