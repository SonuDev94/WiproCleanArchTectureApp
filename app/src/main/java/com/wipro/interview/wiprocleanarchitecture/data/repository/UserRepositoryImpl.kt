package com.wipro.interview.wiprocleanarchitecture.data.repository

import com.wipro.interview.wiprocleanarchitecture.data.models.UserResponse
import com.wipro.interview.wiprocleanarchitecture.data.remote.ApiService
import com.wipro.interview.wiprocleanarchitecture.domain.repository.UserRepository

class UserRepositoryImpl(val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(): Result<List<UserResponse>> {
        return try {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                val data = response.body()
                data?.let {
//                  val data=  it.sortedByDescending { it.name }
                    Result.success(it)
                } ?: Result.failure(Exception("No Data"))
            } else {
                Result.failure(Exception("Failed"))
            }

        } catch (e: Exception) {
            Result.failure(Exception("Failed"))
        }
    }
}

//https://jsonplaceholder.typicode.com/users