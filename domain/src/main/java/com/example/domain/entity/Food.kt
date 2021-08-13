package com.example.domain.entity

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 *
 * Entity class for food item
 */
data class Food(
	val id: String,
	val imageUrl: String?,
	val name: String,
	val price: Double?,
	val rating: Double,
	val categories: List<String> = emptyList(),
)
