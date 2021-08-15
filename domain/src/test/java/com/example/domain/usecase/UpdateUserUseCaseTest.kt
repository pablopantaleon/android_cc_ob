package com.example.domain.usecase

import app.cash.turbine.test
import com.example.core.DataResult
import com.example.core.UseCaseResult
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.impl.UpdateUserUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Pablo Reyes [devpab@gmail.com] on 14/08/21.
 */
@ExperimentalTime
class UpdateUserUseCaseTest {

	private lateinit var useCase: UpdateUserUseCase
	private val error = Throwable()
	private val user: User = mockk()
	private val name: String = "John Doe"
	private val city: String = "New York"
	private val bio: String = "Hello, this is a test user"
	private val repository: UserRepository = mockk()
	private val states = flow {
		emit(DataResult.Loading)
		emit(DataResult.Success(user))
		emit(DataResult.Failed(error))
	}

	@Before
	fun setUp() {
		every { repository.updateUser(name, city, bio) } returns states
		useCase = UpdateUserUseCaseImpl(repository)
	}

	@Test
	fun `should transform DataResult to UseCaseResult`() = runBlocking {
		useCase.invoke(name, city, bio).test {
			Assert.assertEquals(UseCaseResult.Loading, awaitItem())
			Assert.assertEquals(UseCaseResult.Success(user), awaitItem())
			Assert.assertEquals(UseCaseResult.Failed(error), awaitItem())
			awaitComplete()
		}
	}
}