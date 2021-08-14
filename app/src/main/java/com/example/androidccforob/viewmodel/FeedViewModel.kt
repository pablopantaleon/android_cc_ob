package com.example.androidccforob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidccforob.feed.FeedDataAdapter
import com.example.domain.entity.Food
import com.example.domain.usecase.GetFoodItemsUseCase
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
	getFoodItemsUseCase: GetFoodItemsUseCase,
) : ViewModel() {

	val allFoodItemsState = getFoodItemsUseCase.invoke().stateIn(
		viewModelScope,
		SharingStarted.WhileSubscribed(500),
		UseCaseResult.Loading
	)

	fun onFilterChanged(categoryId: String, food: UseCaseResult.Succeed<List<Food>>): List<Food> {
		return if (categoryId == FeedDataAdapter.FILTER_ALL) {
			food.data
		} else {
			food.data.filter { it.categories.contains(categoryId) }
		}
	}
}