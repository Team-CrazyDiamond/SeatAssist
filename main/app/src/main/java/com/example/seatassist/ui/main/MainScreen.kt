package com.example.seatassist.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.data.OffsetData
import com.example.seatassist.ui.components.MainPlaceholder
import kotlin.math.roundToInt

/**

 **/
@Composable
fun MainScreen(
    numberText: String,
    menu: List<String> = listOf("Number of seats", "Members", "Seats size"),
    offsetList: List<OffsetData>,
    onMembersClick: () -> Unit,
    onSizeClick: () -> Unit,
    onEditNumber: (String) -> Unit,
    onAddObject: (Int, Float, Float) -> Unit,
    onRemoveObject: (Int) -> Unit,
    onMoveOffsetX: (Int, Float) -> Unit,
    onMoveOffsetY: (Int, Float) -> Unit
) {
    BoxWithConstraints {
        val screenWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
        val screenHeight = with(LocalDensity.current) { constraints.maxHeight.toDp() }

        Column(
            modifier = Modifier.wrapContentHeight()
        ) {
            val state = rememberScrollState()
            val stateValue =
                if (state.value <= screenHeight.value * 0.3) state.value else (screenHeight.value * 0.3).toInt()

            Box(
                modifier = Modifier
                    .width(screenWidth)
                    .height((screenHeight.value * 0.85F - stateValue).dp)
                    .pointerInput(Unit) {
                        detectTapGestures { onAddObject(offsetList.size, it.x, it.y) }
                    }
            ) {
                // 操作画面をここに記述
                offsetList.forEach { offsetData ->
                    DragBox(
                        id = offsetData.id,
                        offsetX = offsetData.offsetX.value,
                        offsetY = offsetData.offsetY.value,
                        onMoveOffsetX = onMoveOffsetX,
                        onMoveOffsetY = onMoveOffsetY,
                        onRemoveObject = onRemoveObject
                    )
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
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    )
                    Text(
                        text = "Write the menu description here",
                        color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 16.dp
                        )
                    )
                    MainNumber(text = menu[0], numberText = numberText, onEditNumber = onEditNumber)
                    MainMenuItem(text = menu[1], onClick = onMembersClick)
                    MainMenuItem(text = menu[2], onClick = onSizeClick)
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
fun MainMenuItem(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h4,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Divider(
            color = MaterialTheme.colors.onPrimary.copy(alpha = ContentAlpha.medium),
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainNumber(
    text: String = "Number of seats",
    numberText: String,
    onEditNumber: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            Text(
                text = text,
                style = MaterialTheme.typography.h4,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            TextField(
                value = numberText,
                onValueChange = { onEditNumber(it) },
                placeholder = { MainPlaceholder(text = "Input text") },
                textStyle = MaterialTheme.typography.h4.copy(
                    fontSize = 26.sp,
                    textAlign = TextAlign.End,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.Transparent
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })
            )
        }
        Divider(
            color = MaterialTheme.colors.onPrimary.copy(alpha = ContentAlpha.medium),
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun DragBox(
    id: Int,
    offsetX: Float,
    offsetY: Float,
    onMoveOffsetX: (Int, Float) -> Unit,
    onMoveOffsetY: (Int, Float) -> Unit,
    onRemoveObject: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .offset { IntOffset(x = offsetX.roundToInt(), y = offsetY.roundToInt()) }
            .background(color = Color.Black)
            .size(50.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    onMoveOffsetX(id, dragAmount.x)
                    onMoveOffsetY(id, dragAmount.y)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(onDoubleTap = { onRemoveObject(id) })
            }
            .background(color = MaterialTheme.colors.primary)
    )
}