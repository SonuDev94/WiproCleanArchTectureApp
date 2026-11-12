package com.wipro.interview.wiprocleanarchitecture.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen() {
    var isSearchActive by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()


    Scaffold(
        Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection), topBar = {
            if (isSearchActive) {
                SearchAppBar(query = query, onQueryChanged = {
                    query = it
                }, onCloseClick = {
                    query = ""
                    isSearchActive = false
                }, onSearchTriggered = {
                    query = it

                }, scrollBehaviour)
            } else {
                DefaultAppBar(scrollBehaviour) {
                    isSearchActive = true
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(scrollBehavior: TopAppBarScrollBehavior, onSearchClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray),
        title = { Text("My App") },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFD0E8FF)),
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSearchTriggered: (String) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TopAppBar(
        title = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = onQueryChanged,
                placeholder = { Text(text = "Search...", color = Color.DarkGray) },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(onClick = onCloseClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Arrow"
                        )
                    }
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { onQueryChanged("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear Text"
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchTriggered(query)
                        keyboardController?.hide()
                    }
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFD0E8FF)),
        scrollBehavior = scrollBehavior
    )
}
