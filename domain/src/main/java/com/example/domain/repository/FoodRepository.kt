package com.example.domain.repository

import com.example.domain.entity.Food
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 */
interface FoodRepository {

	/**
	 * Get all food items
	 * @return List of food items
	 */
	fun getAllFoodItems(): Flow<DataResult<List<Food>>>

	/**
	 * Get all food items using a [categoryId] as a filter value
	 * @return List of food items associated to the provided [categoryId]
	 */
	fun getFoodItemsByCategoryId(categoryId: String): Flow<DataResult<List<Food>>>

}