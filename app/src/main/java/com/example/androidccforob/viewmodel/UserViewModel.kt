package com.example.androidccforob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.IsUserLoggedInUseCase
import com.example.domain.usecase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class UserViewModel @Inject constructor(
	isUserLoggedInUseCase: IsUserLoggedInUseCase,
) : ViewModel() {

	val state = isUserLoggedInUseCase.invoke().stateIn(
		viewModelScope,
		SharingStarted.Lazily,
		UseCaseResult.Loading
	)
}