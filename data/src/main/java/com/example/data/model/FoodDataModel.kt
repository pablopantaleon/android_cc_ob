package com.example.data.model

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
data class FoodDataModel(
	val id: String? = null,
	val imageUrl: String? = null,
	val name: String? = null,
	val price: Double? = null,
	val rating: Int? = null,
	val categories: List<String> = emptyList(),
)