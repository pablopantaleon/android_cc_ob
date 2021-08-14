package com.example.domain.entity

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 14/08/21.
 */
data class LikedTransaction(
	val foodId: String,
	val liked: Boolean,
	val success: Boolean,
)