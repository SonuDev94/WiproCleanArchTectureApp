package com.wipro.interview.wiprocleanarchitecture.domain.usecases

import com.wipro.interview.wiprocleanarchitecture.data.models.UserResponse
import com.wipro.interview.wiprocleanarchitecture.domain.repository.UserRepository
import com.wipro.interview.wiprocleanarchitecture.domain.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserUseCase @Inject constructor(val userRepository: UserRepository) {
    operator fun invoke(): Flow<NetworkResult<List<UserResponse>>> {
        return flow {
            emit(NetworkResult.Loading())

            val result = userRepository.getUsers()

            if (result.isSuccess) {

                emit(NetworkResult.Success(result.getOrThrow()))
            } else {
                emit(NetworkResult.Error("Not Found"))
            }
        }.catch {
            emit(NetworkResult.Error(it.message ?: "Error"))
        }.flowOn(Dispatchers.IO)

    }
}