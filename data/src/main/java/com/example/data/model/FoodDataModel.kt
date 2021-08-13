package com.example.data.model

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
data class FoodDataModel(
	val id: String = "",
	val imageUrl: String? = null,
	val name: String? = null,
	val price: Double? = null,
	val rating: Double? = null,
	val categories: List<String> = emptyList(),
)