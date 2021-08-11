package com.example.data.model

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
data class UserDataModel(
	val id: String,
	val avatarUrl: String? = null,
	val bio: String? = null,
	val city: String? = null,
	val email: String? = null,
	val likes: List<String> = emptyList(),
	val name: String? = null,
)
