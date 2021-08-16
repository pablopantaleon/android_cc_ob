package com.example.data.repository

import app.cash.turbine.test
import com.example.core.DataResult
import com.example.data.datasource.FirebaseDataSource
import com.example.data.mapper.EntityMapper
import com.example.data.model.FoodDataModel
import com.example.data.model.UserDataModel
import com.example.domain.entity.Food
import com.example.domain.repository.FoodRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 15/08/21.
 */
@ExperimentalTime
class FoodRepositoryImplTest {

	private val dataSource: FirebaseDataSource = mockk()
	private val entityMapper: EntityMapper = mockk()
	private lateinit var repository: FoodRepository
	private lateinit var food: Food
	private lateinit var foodDataModel: FoodDataModel
	private lateinit var error: Throwable

	@Before
	fun setUp() {
		repository = FoodRepositoryImpl(dataSource, entityMapper)
		error = Throwable()
		foodDataModel = FoodDataModel()
		food = Food(
			id = "1111",
			imageUrl = "https://example.com/image.jpg",
			name = "banana",
			price = 100.0,
			rating = 20.0,
			categories = emptyList(),
		)
	}

	@Test
	fun `when get food items, should return a list of foods`() = runBlocking {
		// arrange
		val foodDataModels = listOf(foodDataModel)
		val foods = listOf(food)
		val userDataModel = UserDataModel()
		coEvery { dataSource.getFoodItems() } returns foodDataModels
		coEvery { dataSource.getUser() } returns userDataModel
		every { entityMapper.toFoodList(foodDataModels, userDataModel) } returns foods
		// act & assert
		repository.getFoodItems().test {
			assertEquals(DataResult.Loading, awaitItem())
			assertEquals(DataResult.Success(foods), awaitItem())
			awaitComplete()
		}
	}

	@Test
	fun `when get food items fails, should throw an error and return a Failed state`() =
		runBlocking {
			// arrange
			coEvery { dataSource.getFoodItems() } throws error
			// act & assert
			repository.getFoodItems().test {
				assertEquals(DataResult.Loading, awaitItem())
				assertEquals(DataResult.Failed(error), awaitItem())
				awaitComplete()
			}
		}
}