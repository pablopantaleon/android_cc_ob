package com.example.data.mapper

import com.example.data.model.FoodDataModel
import com.example.data.model.UserDataModel
import com.example.domain.entity.Food
import com.example.domain.entity.User
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 14/08/21.
 */
class EntityMapperTest {

	private lateinit var entityMapper: EntityMapper
	private val userDataModel: UserDataModel = mockk()

	@Before
	fun setUp() {
		entityMapper = EntityMapper()
	}

	@Test
	fun `when receive a UserDataModel should return User`() {
		// arrange
		val data = UserDataModel(
			id = "1111",
			avatarUrl = "https://example.com/image.jpg",
			bio = "bio",
			city = "city",
			email = "example@go.com",
			likes = emptyList(),
			name = "John Doe",
		)
		val user = User(
			id = data.id,
			avatarUrl = data.avatarUrl ?: "",
			bio = data.bio ?: "",
			city = data.city ?: "",
			email = data.email ?: "",
			likes = emptyList(),
			name = data.name ?: "",
		)
		// act
		val result = entityMapper.toUser(data)
		// assert
		assertThat(result, `is`(user))
	}

	@Test
	fun `when receive a FoodDataModel should return Food`() {
		// arrange
		every { userDataModel.likes } returns emptyList()
		val data = FoodDataModel(
			id = "1111",
			imageUrl = "https://example.com/image.jpg",
			name = "banana",
			price = 100.0,
			rating = 20.0,
			categories = emptyList(),
		)
		val foodData = listOf(data)
		val foods = listOf(
			Food(
				id = data.id,
				imageUrl = data.imageUrl,
				name = data.name ?: "",
				price = data.price?.div(100),
				rating = data.rating ?: 0.0,
				isLiked = userDataModel.likes?.contains(data.id) ?: false,
				categories = data.categories,
			)
		)
		// act
		val result = entityMapper.toFoodList(foodData, userDataModel)
		// assert
		assertThat(result, `is`(foods))
	}
}