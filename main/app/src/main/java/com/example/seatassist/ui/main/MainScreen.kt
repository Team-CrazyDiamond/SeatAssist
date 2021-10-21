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
import com.example.seatassist.ui.components.*
import kotlin.math.roundToInt

/**

 **/
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    numberText: String,
    sizeValue: Dp,
    scaleValue: ScaleData,
    dragColor: Color,
    menu: List<String> = listOf("Number of seats", "Members", "Custom"),
    offsetList: List<OffsetData>,
    onMembersClick: () -> Unit,
    onSizeClick: () -> Unit,
    onEditNumber: (String) -> Unit,
    onAddObject: (Int, String, Float, Float, Color, Dp) -> Unit,
    onRemoveObject: (Int) -> Unit,
    onMoveOffsetX: (Int, Float) -> Unit,
    onMoveOffsetY: (Int, Float) -> Unit,
    onShuffleList: () -> Unit,
    onLotteryClick: () -> Unit
) {
    BackdropScaffold(
        appBar = { },
        backLayerContent = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onAddObject(
                                offsetList.size,
                                "",
                                it.x,
                                it.y,
                                dragColor,
                                sizeValue * scaleValue.scale.value
                            )
                        }
                    }
            ) {
                offsetList.forEach { offsetData ->
                    DragBox(
                        id = offsetData.id,
                        color = offsetData.color,
                        offsetX = offsetData.offsetX.value,
                        offsetY = offsetData.offsetY.value,
                        sizeValue = offsetData.size,
                        onMoveOffsetX = onMoveOffsetX,
                        onMoveOffsetY = onMoveOffsetY,
                        onRemoveObject = onRemoveObject,
                        dragBoxContent = {  }
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
                MainEditText(
                    text = menu[0],
                    editText = numberText,
                    placeholderText = "Input number",
                    onEditText = onEditNumber
                )
                MainMenuItem(text = menu[1], onClick = onMembersClick)
                MainMenuItem(text = menu[2], onClick = onSizeClick)
                Spacer(modifier = Modifier.size(16.dp))
                LotteryRestButton(onShuffleList = onShuffleList, onLotteryClick = onLotteryClick)
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

@Composable
fun LotteryRestButton(
    onShuffleList: () -> Unit,
    onLotteryClick: () -> Unit
) {
    SubText(
        text = "Once you have entered all the information, please click on the lottery button." +
                " There is also a reset button.",
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )
    MainButton(
        text = "Lottery",
        color = MaterialTheme.colors.primary,
        onClick = {
            onShuffleList()
            onLotteryClick()
        }
    )
    MainButton(
        text = "Reset",
        color = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary,
        borderColor = MaterialTheme.colors.primary
    )
    Spacer(modifier = Modifier.size(16.dp))
}