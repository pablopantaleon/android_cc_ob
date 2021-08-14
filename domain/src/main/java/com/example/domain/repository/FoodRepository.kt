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
	fun getFoodItems(): Flow<DataResult<List<Food>>>

}