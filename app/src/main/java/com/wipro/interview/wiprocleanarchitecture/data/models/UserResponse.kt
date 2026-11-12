package com.wipro.interview.wiprocleanarchitecture.data.models

data class UserResponse(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address

)

data class Address(val street: String)
