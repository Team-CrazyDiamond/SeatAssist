package com.example.seatassist.ui.main

import android.util.Log
import android.view.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.NonDisposableHandle.parent

@Preview(showBackground = true)
@Composable
fun MainScreen(menu: List<String> = listOf("Number of seats", "Members", "Seats size")) {
    BoxWithConstraints {
        val screenWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
        val screenHeight = with(LocalDensity.current) { constraints.maxHeight.toDp() }

        Column(
            modifier = Modifier.wrapContentHeight()
        ) {
            val state = rememberScrollState()
            val stateValue = if (state.value <= screenHeight.value * 0.3) state.value else (screenHeight.value * 0.3).toInt()

            Box(
                modifier = Modifier
                    .width(screenWidth)
                    .height((screenHeight.value * 0.85F - stateValue).dp)
            ) {
                // 操作画面をここに記述,
                Text(text = "test")
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(top = screenHeight / 3)
                        .size(300.dp)
                        .wrapContentHeight()
                ) {
                    Text("Button", fontSize = 30.sp)
                }
            }

            Surface(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .height((screenHeight.value * 0.15F + stateValue).dp)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .scrollable(
                            orientation = Orientation.Vertical,
                            state = state,
                            reverseDirection = true,
                        )
                ) {
                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.h6,
                        fontSize = 34.sp,
                        modifier = Modifier.padding(start = 16.dp, end =16.dp, top = 16.dp)
                    )
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                        Text(
                            text = "Write the menu description here",
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                        )
                    }
                    menu.forEach {
                        MainMenuItem(text = it)
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
fun MainMenuItem(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h4,
        fontSize = 20.sp,
        modifier = Modifier.padding(16.dp)
    )
    Divider(
        color = MaterialTheme.colors.background.copy(alpha = ContentAlpha.medium),
        modifier = Modifier
            .padding(start = 16.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun MainNumber(text: String = "Number of seats") {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var numberText by remember { mutableStateOf("Input text") }
        Text(
            text = text,
            style = MaterialTheme.typography.h4,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = numberText,
            onValueChange = { numberText = it },
            modifier = Modifier
                .wrapContentSize(align = Alignment.CenterEnd)
                .background(color = MaterialTheme.colors.primary),
            textStyle = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.background, fontSize = 20.sp),
        )
    }
}
