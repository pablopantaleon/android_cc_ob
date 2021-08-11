package com.example.data.mapper

import com.example.data.model.UserDataModel
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
}