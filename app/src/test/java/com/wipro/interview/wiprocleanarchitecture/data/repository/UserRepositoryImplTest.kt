package com.wipro.interview.wiprocleanarchitecture.data.repository

import com.wipro.interview.wiprocleanarchitecture.data.models.Address
import com.wipro.interview.wiprocleanarchitecture.data.models.UserResponse
import com.wipro.interview.wiprocleanarchitecture.data.remote.ApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class UserRepositoryImplTest {
    val apiService: ApiService = mockk()
    val repo = UserRepositoryImpl(apiService)

    @Test
    fun test_success() = runTest {
        coEvery { apiService.getUsers() } returns Response.success(getUserResponse())
        val response = repo.getUsers()
        assertTrue(response.isSuccess)

        assertEquals(response.getOrThrow(), getUserResponse())
    }

    @Test
    fun test_failed() = runTest {
        coEvery { apiService.getUsers() } returns Response.error(400, "".toResponseBody(null))
        val result = repo.getUsers()

        assertTrue(result.isFailure)

        assertEquals(result.exceptionOrNull()?.message, "Failed")
    }

    @Test
    fun test_failed_throw_exception() = runTest {
        coEvery { apiService.getUsers() } throws RuntimeException("Failed")
        val resut = repo.getUsers()

        assertTrue(resut.isFailure)

        assertEquals(resut.exceptionOrNull()?.message,"Failed")
    }

}

fun getUserResponse(): List<UserResponse> {
    return listOf(
        UserResponse(
            id = 1,
            name = "Ac",
            email = "ab@Gmail.com",
            username = "ABC",
            address = Address(street = "Gurgon")
        )
    )
}