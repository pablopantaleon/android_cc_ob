package com.example.androidccforob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidccforob.feed.FeedDataAdapter
import com.example.core.UseCaseResult
import com.example.domain.entity.Food
import com.example.domain.entity.LikedTransaction
import com.example.domain.usecase.GetFoodItemsUseCase
import com.example.domain.usecase.UpdateFoodLikedStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 12/08/21.
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class FeedViewModel @Inject constructor(
	getFoodItemsUseCase: GetFoodItemsUseCase,
	private val updateFoodLikedStateUseCase: UpdateFoodLikedStateUseCase,
) : ViewModel() {

	val allFoodItemsState = getFoodItemsUseCase.invoke().stateIn(
		viewModelScope,
		SharingStarted.WhileSubscribed(500),
		UseCaseResult.Loading
	)
	private val _updateFoodLikeState =
		MutableStateFlow<UseCaseResult<LikedTransaction, Throwable>>(UseCaseResult.Initial)
	val updateFoodLikedState: StateFlow<UseCaseResult<LikedTransaction, Throwable>> =
		_updateFoodLikeState

	fun onFilterChanged(
		categoryId: String,
		food: UseCaseResult.Success<List<Food>>
	): List<String> {
		return if (categoryId == FeedDataAdapter.FILTER_ALL) {
			food.data.map { it.id }
		} else {
			food.data.filter { it.categories.contains(categoryId) }.map { it.id }
		}
	}

	fun updateFoodLikeState(foodId: String, liked: Boolean) {
		viewModelScope.launch {
			updateFoodLikedStateUseCase.invoke(foodId, liked).collect { result ->
				_updateFoodLikeState.value = result
			}
		}
	}
}