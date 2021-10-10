package com.example.seatassist.ui.usage

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UsageScreen(
    onNavigationClick: () -> Unit
) {
    Scaffold(
        topBar = { UsageTopBar(onNavigationClick = onNavigationClick) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
    )
    {
        Text(
            text = "How to use seat assist!!!",
            fontSize = 30.sp,
            style = MaterialTheme.typography.h4
        )
    }
}

@Composable
fun UsageTopBar(onNavigationClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "How to use Seat Assist") },
        navigationIcon = {
            IconButton(onClick = { onNavigationClick() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back button")
            }
        },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
    )
}