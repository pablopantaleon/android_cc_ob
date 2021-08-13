package com.example.data.mapper

import com.example.data.model.FoodDataModel
import com.example.data.model.UserDataModel
import com.example.domain.entity.Food
import com.example.domain.entity.User

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 11/08/21.
 */
class EntityMapper {

	/**
	 * Map [UserDataModel] to [User] entity
	 */
	fun toUser(data: UserDataModel): User {
		return User(
			id = data.id,
			name = data.name ?: "",
			avatarUrl = data.avatarUrl ?: "",
			bio = data.bio ?: "",
			city = data.city ?: "",
			email = data.email ?: "",
			likes = data.likes,
		)
	}

	private fun toFood(data: FoodDataModel): Food {
		return Food(
			id = data.id,
			imageUrl = data.imageUrl,
			name = data.name ?: "",
			price = data.price?.div(100),
			rating = data.rating ?: 0.0,
			categories = data.categories
		)
	}

	fun toFoodList(data: List<FoodDataModel>): List<Food> {
		return data.map { toFood(it) }.toList()
	}
}