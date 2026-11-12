package com.wipro.interview.wiprocleanarchitecture.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wipro.interview.wiprocleanarchitecture.data.models.UserResponse
import com.wipro.interview.wiprocleanarchitecture.domain.usecases.GetUserUseCase
import com.wipro.interview.wiprocleanarchitecture.domain.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val getUserUseCase: GetUserUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun parseIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.GetUserIntent -> {
                getUser()
            }
        }
    }

    fun getUser() {
        getUserUseCase.invoke().onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.value = UIState(isLoading = true)
                }

                is NetworkResult.Error -> {
                    _uiState.value = UIState(error = result.message)
                }

                is NetworkResult.Success -> {
                    _uiState.value = UIState(data = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class UIState(
    val isLoading: Boolean = false,
    val data: List<UserResponse>? = null,
    val error: String = ""
)

sealed interface UserIntent {
    data object GetUserIntent : UserIntent
}