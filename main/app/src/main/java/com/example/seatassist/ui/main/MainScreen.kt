package com.example.seatassist.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.data.OffsetData
import com.example.seatassist.data.ScaleData
import com.example.seatassist.ui.components.MainButton
import com.example.seatassist.ui.components.MainDivider
import com.example.seatassist.ui.components.MainPlaceholder
import com.example.seatassist.ui.components.SubText
import kotlin.math.roundToInt

/**

 **/
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    numberText: String,
    sizeValue: Dp,
    scaleValue: ScaleData,
    dragColor: Color,
    menu: List<String> = listOf("Number of seats", "Members", "Seats size"),
    offsetList: List<OffsetData>,
    onMembersClick: () -> Unit,
    onSizeClick: () -> Unit,
    onEditNumber: (String) -> Unit,
    onAddObject: (Int, Float, Float, Color, Dp) -> Unit,
    onRemoveObject: (Int) -> Unit,
    onMoveOffsetX: (Int, Float) -> Unit,
    onMoveOffsetY: (Int, Float) -> Unit
) {
    BackdropScaffold(
        appBar = { },
        backLayerContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onAddObject(
                                offsetList.size,
                                it.x,
                                it.y,
                                dragColor,
                                sizeValue * scaleValue.scale.value
                            )
                        }
                    }
            ) {
                // 操作画面をここに記述
                offsetList.forEach { offsetData ->
                    DragBox(
                        id = offsetData.id,
                        color = offsetData.color,
                        offsetX = offsetData.offsetX.value,
                        offsetY = offsetData.offsetY.value,
                        sizeValue = offsetData.size,
                        onMoveOffsetX = onMoveOffsetX,
                        onMoveOffsetY = onMoveOffsetY,
                        onRemoveObject = onRemoveObject
                    )
                }
            }
        },
        frontLayerContent = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Menu",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    style = MaterialTheme.typography.h6,
                    fontSize = 34.sp,
                )
                SubText(
                    text = "Write the menu description here",
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
                LotteryRestButton()
            }
        },
        scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed),
        peekHeight = 170.dp,
        headerHeight = 110.dp,
        backLayerBackgroundColor = MaterialTheme.colors.primary,
        backLayerContentColor = MaterialTheme.colors.primaryVariant,
        frontLayerBackgroundColor = MaterialTheme.colors.onPrimary,
        frontLayerContentColor = MaterialTheme.colors.primary,
        frontLayerScrimColor = Color.Unspecified,
        frontLayerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        frontLayerElevation = 8.dp
    )
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
        MainDivider()
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
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.Transparent
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })
            )
        }
        MainDivider()
    }
}

@Composable
fun DragBox(
    id: Int,
    color: Color,
    offsetX: Float,
    offsetY: Float,
    sizeValue: Dp,
    onMoveOffsetX: (Int, Float) -> Unit,
    onMoveOffsetY: (Int, Float) -> Unit,
    onRemoveObject: (Int) -> Unit
) {
    Surface(
        color = color,
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .offset { IntOffset(x = offsetX.roundToInt(), y = offsetY.roundToInt()) }
            .size(sizeValue)
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
    ) {
        // 名前を記述する処理
    }
}

@Composable
fun LotteryRestButton() {
    SubText(
        text = "Once you have entered all the information, please click on the lottery button." +
                " There is also a reset button.",
        modifier = Modifier.padding(start = 16.dp, end = 19.dp, top = 16.dp)
    )
    MainButton(
        text = "Lottery",
        color = MaterialTheme.colors.primary,
    )
    MainButton(
        text = "Reset",
        color = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary,
        borderColor = MaterialTheme.colors.primary
    )
    Spacer(modifier = Modifier.size(16.dp))
}