package com.example.androidccforob.feed

import com.example.domain.entity.Food
import javax.inject.Inject

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 13/08/21.
 *
 * Helper class that transforms entity data to UI representative based data-class
 */
class FeedDataAdapter @Inject constructor() {

	/**
	 * Get all food items for the feed
	 * @param foods: Food entities
	 * @return List of [Food] representative UI
	 */
	fun getFeedFoodData(foods: List<Food>): List<FoodListItem> {
		return foods.map { food ->
			FoodListItem(
				id = food.id,
				imageUrl = food.imageUrl,
				name = food.name,
				price = food.price,
				rating = food.rating,
				isLiked = food.isLiked,
			)
		}
	}

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

/**
 * [Food] category UI representative data-class
 */
data class FoodCategoryListItem(
	val name: String,
	var isSelected: Boolean,
)

/**
 * [Food] UI representative data-class
 */
data class FoodListItem(
	val id: String,
	val imageUrl: String?,
	val name: String,
	val price: Double?,
	val rating: Double,
	var isLiked: Boolean = false,
)

