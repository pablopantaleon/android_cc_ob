package com.example.androidccforob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAllFoodItemsUseCase
import com.example.domain.usecase.GetFoodItemsByCategoryUseCase
import com.example.domain.usecase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 12/08/21.
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class FeedViewModel @Inject constructor(
	getAllFoodItemsUseCase: GetAllFoodItemsUseCase,
	private val getFoodItemsByCategoryUseCase: GetFoodItemsByCategoryUseCase,
) : ViewModel() {

	val allFoodItemsState = getAllFoodItemsUseCase.invoke().stateIn(
		viewModelScope,
		SharingStarted.WhileSubscribed(500),
		UseCaseResult.Loading
	)

}