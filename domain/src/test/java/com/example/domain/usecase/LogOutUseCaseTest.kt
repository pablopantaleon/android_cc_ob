package com.example.domain.usecase

import app.cash.turbine.test
import com.example.core.DataResult
import com.example.core.UseCaseResult
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.impl.LogOutUseCaseImpl
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
class LogOutUseCaseTest {

	private lateinit var useCase: LogOutUseCase
	private val error = Throwable()
	private val repository: UserRepository = mockk()
	private val states = flow {
		emit(DataResult.Loading)
		emit(DataResult.Success(Unit))
		emit(DataResult.Failed(error))
	}

	@Before
	fun setUp() {
		every { repository.logOut() } returns states
		useCase = LogOutUseCaseImpl(repository)
	}

	@Test
	fun `should transform DataResult to UseCaseResult`() = runBlocking {
		useCase.invoke().test {
			Assert.assertEquals(UseCaseResult.Loading, awaitItem())
			Assert.assertEquals(UseCaseResult.Success(Unit), awaitItem())
			Assert.assertEquals(UseCaseResult.Failed(error), awaitItem())
			awaitComplete()
		}
	}
}