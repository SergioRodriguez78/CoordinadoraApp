package com.coordinadora.coordinadoraapp.onboarding.login.viewmodel

import com.coordinadora.coordinadoraapp.core.ScreenState
import com.coordinadora.coordinadoraapp.database.dao.UserDao
import com.coordinadora.coordinadoraapp.firebase.FirebaseManager
import com.coordinadora.coordinadoraapp.onboarding.login.data.remote.AuthenticationService
import com.coordinadora.coordinadoraapp.onboarding.login.model.dto.AuthenticationResponseDto
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private val mockService: AuthenticationService = mockk()
    private val mockDao: UserDao = mockk(relaxed = true)
    private val mockFirebase: FirebaseManager = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = LoginViewModel(mockService, mockDao, mockFirebase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `login success updates screen state to success and saves user`() = runTest(
        StandardTestDispatcher()
    ) {
        // Given
        val username = "testUser"
        val password = "testPassword"
        val dto =
            AuthenticationResponseDto(isError = false, message = "Success", periodValidation = 30)

        coEvery { mockService.login(username, password) } answers {
            Result.success(dto)
        }
        coEvery {
            mockFirebase.createRemoteUser(dto.periodValidation)
        } returns Unit

        // When
        viewModel.login(username, password)

        // Then
        assert(viewModel.screenState.value is ScreenState.Success)

        coVerify {
            mockDao.save(withArg {
                assert(it.username == username)
            })
        }
        coVerify { mockFirebase.createRemoteUser(dto.periodValidation) }
    }

    @Test
    fun `login failure updates screen state to error`() = runTest  {
        // Given
        val username = "testUser"
        val password = "testPassword"
        val errorMessage = "Login failed"

        coEvery { mockService.login(username, password) } answers {
            Result.failure(Exception(errorMessage))
        }

        // When
        viewModel.login(username, password)

        // Then
        assert(viewModel.screenState.value is ScreenState.Error)
    }

    @Test
    fun `dismissDialog clears username and password and resets state`() {
        // Given
        viewModel.updateUsername("testUser")
        viewModel.updatePassword("testPassword")

        // When
        viewModel.dismissDialog()

        // Then
        assert(viewModel.screenState.value is ScreenState.None)
        assert(viewModel.username.value.isEmpty())
        assert(viewModel.password.value.isEmpty())
    }
}
