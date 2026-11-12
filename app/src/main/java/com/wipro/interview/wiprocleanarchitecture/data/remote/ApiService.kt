package com.wipro.interview.wiprocleanarchitecture.data.remote

import com.wipro.interview.wiprocleanarchitecture.data.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    //https://jsonplaceholder.typicode.com/users
    @GET("users")
   suspend fun getUsers(): Response<List<UserResponse>>
}