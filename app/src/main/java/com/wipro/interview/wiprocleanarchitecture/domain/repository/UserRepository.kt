package com.wipro.interview.wiprocleanarchitecture.domain.repository

import com.wipro.interview.wiprocleanarchitecture.data.models.UserResponse

interface UserRepository {
   suspend fun getUsers() : Result<List<UserResponse>>
}