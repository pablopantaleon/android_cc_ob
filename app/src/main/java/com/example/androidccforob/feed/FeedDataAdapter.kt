package com.example.androidccforob.feed

import com.example.domain.entity.Food
import javax.inject.Inject

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 13/08/21.
 */

class FeedDataAdapter @Inject constructor() {

	/**
	 * Get all food categories
	 * @param foods: List of food elements
	 * @return List of categories associated to the provided foods
	 */
	fun getFeedFoodCategories(foods: List<Food>): List<FoodCategoryListItem> {
		return foods.flatMap { it.categories }
			.toSet()
			.toMutableList()
			.also { it.add(0, FILTER_ALL) }
			.map { FoodCategoryListItem(it, false) }
	}

	companion object {
		const val FILTER_ALL = "All"
	}
}

data class FoodCategoryListItem(
	val name: String,
	var isSelected: Boolean,
)

