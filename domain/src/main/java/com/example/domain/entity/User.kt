package com.example.domain.entity

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 9/08/21.
 *
 * Entity class for User
 */
data class User(
	val id: String,
	val name: String,
	val avatarUrl: String,
	val bio: String,
	val city: String,
	val email: String,
	val likes: List<String> = emptyList(),
)
