package com.wipro.interview.wiprocleanarchitecture.presentation.viewmodels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun UserScreen(modifier: Modifier = Modifier) {
    val viewModel: UserViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(null) {
        viewModel.parseIntent(UserIntent.GetUserIntent)
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (uiState.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(uiState.error)
        }
    }

    uiState.data?.let { list ->
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list) {
                Column(modifier) {
                    Text("UserName: ${it.username}")
                    Text("email: ${it.email}")
                    Text("street: ${it.address.street}")
                }
            }
        }
    }
}