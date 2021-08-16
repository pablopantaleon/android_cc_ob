package com.example.data.repository

import app.cash.turbine.test
import com.example.core.DataResult
import com.example.data.datasource.FirebaseDataSource
import com.example.data.mapper.EntityMapper
import com.example.data.model.UserDataModel
import com.example.domain.entity.LikedTransaction
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 15/08/21.
 */
@ExperimentalTime
class UserRepositoryImplTest {

	private val dataSource: FirebaseDataSource = mockk()
	private val entityMapper: EntityMapper = mockk()
	private lateinit var repository: UserRepository
	private lateinit var user: User
	private lateinit var userDataModel: UserDataModel
	private lateinit var error: Throwable

	@Before
	fun setUp() {
		repository = UserRepositoryImpl(dataSource, entityMapper)
		error = Throwable()
		userDataModel = UserDataModel()
		user = User(
			id = "11111111",
			avatarUrl = "https://example.com/image.jpg",
			bio = "this is my bio",
			city = "this is my city",
			email = "this is the email",
			likes = emptyList(),
			name = "John Doe",
		)
	}

	@Test
	fun `when login with credentials should return a User`() = runBlocking {
		val email = "example@go.com"
		val password = "password"
		coEvery { dataSource.logInWithCredentials(email, password) } returns userDataModel
		every { entityMapper.toUser(userDataModel) } returns user
		repository.logInWithCredentials(email, password).test {
			assertEquals(DataResult.Loading, awaitItem())
			assertEquals(DataResult.Success(user), awaitItem())
			awaitComplete()
		}
	}

	@Test
	fun `when login with credentials fails should throw an exception and return Failed state`() =
		runBlocking {
			val email = "example@go.com"
			val password = "password"
			coEvery { dataSource.logInWithCredentials(email, password) } throws error
			repository.logInWithCredentials(email, password).test {
				assertEquals(DataResult.Loading, awaitItem())
				assertEquals(DataResult.Failed(error), awaitItem())
				awaitComplete()
			}
		}

	@Test
	fun `when is user logged in, should return true`() = runBlocking {
		coEvery { dataSource.observeUserLoggedInState() } returns flowOf(true)
		repository.isUserLoggedIn().test {
			assertEquals(DataResult.Success(true), awaitItem())
			awaitComplete()
		}
	}

	@Test
	fun `when get user, should user data model`() = runBlocking {
		coEvery { dataSource.getUser() } returns userDataModel
		every { entityMapper.toUser(userDataModel) } returns user
		repository.getUser().test {
			assertEquals(DataResult.Loading, awaitItem())
			assertEquals(DataResult.Success(user), awaitItem())
			awaitComplete()
		}
	}

	@Test
	fun `when get user fails, should throw an exception and return Failed state`() = runBlocking {
		coEvery { dataSource.getUser() } throws error
		repository.getUser().test {
			assertEquals(DataResult.Loading, awaitItem())
			assertEquals(DataResult.Failed(error), awaitItem())
			awaitComplete()
		}
	}

	@Test
	fun `when update user, should user data model`() = runBlocking {
		coEvery { dataSource.updateUser() } returns userDataModel
		every { entityMapper.toUser(userDataModel) } returns user
		repository.updateUser().test {
			assertEquals(DataResult.Loading, awaitItem())
			assertEquals(DataResult.Success(user), awaitItem())
			awaitComplete()
		}
	}

	@Test
	fun `when update fails, should throw an exception and return Failed state`() = runBlocking {
		coEvery { dataSource.updateUser() } throws error
		repository.updateUser().test {
			assertEquals(DataResult.Loading, awaitItem())
			assertEquals(DataResult.Failed(error), awaitItem())
			awaitComplete()
		}
	}

	@Test
	fun `when update food liked state, should return liked transaction`() = runBlocking {
		val foodId = "1111"
		val liked = true
		val likedTransaction = LikedTransaction(foodId, liked, true)
		coEvery { dataSource.updateFoodLikedState(foodId, liked) } returns Unit
		repository.updateFoodLikedState(foodId, liked).test {
			assertEquals(DataResult.Loading, awaitItem())
			assertEquals(DataResult.Success(likedTransaction), awaitItem())
			awaitComplete()
		}
	}

	@Test
	fun `when update food liked state fails, should throw an exception and return Failed state`() =
		runBlocking {
			val foodId = "1111"
			val liked = true
			coEvery { dataSource.updateFoodLikedState(foodId, liked) } throws error
			repository.updateFoodLikedState(foodId, liked).test {
				assertEquals(DataResult.Loading, awaitItem())
				assertEquals(
					DataResult.Success(
						LikedTransaction(foodId, liked = false, success = false)
					), awaitItem()
				)
				awaitComplete()
			}
		}
}