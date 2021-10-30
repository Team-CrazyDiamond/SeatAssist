package com.example.seatassist.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.R
import com.example.seatassist.data.MembersData
import com.example.seatassist.data.OffsetData
import com.example.seatassist.data.ScaleData
import com.example.seatassist.ui.components.*
import com.google.accompanist.systemuicontroller.SystemUiController

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
    membersList: List<MembersData>,
    offsetList: List<OffsetData>,
    onMembersClick: () -> Unit,
    onSizeClick: () -> Unit,
    onEditNumber: (String) -> Unit,
    onAddObject: (Int, String, Float, Float, Color, Dp) -> Unit,
    onRemoveObject: (Int) -> Unit,
    onRemoveAllObject: () -> Unit,
    onMoveOffsetX: (Int, Float) -> Unit,
    onMoveOffsetY: (Int, Float) -> Unit,
    onShuffleList: () -> Unit,
    onLotteryClick: () -> Unit,
    systemUiController: SystemUiController
) {
    val Sidecar = MaterialTheme.colors.primary
    val pickledBlueWood = MaterialTheme.colors.onPrimary
    val darkIcons = MaterialTheme.colors.isLight
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Sidecar,
            darkIcons = darkIcons
        )
        systemUiController.setNavigationBarColor(
            color = pickledBlueWood,
            darkIcons = false
        )
    }
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
                        dragBoxContent = { }
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
                    fontFamily = fontsBold,
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
                LotteryRestButton(
                    onShuffleList = onShuffleList,
                    onLotteryClick = onLotteryClick,
                    onRemoveAllObject = onRemoveAllObject,
                    membersList = membersList.map { it.name.value },
                    offsetList = offsetList
                )
            }
        },
        peekHeight = 172.dp,
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
            fontFamily = fontsNormal,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        MainDivider()
    }
}

@Composable
fun LotteryRestButton(
    onShuffleList: () -> Unit,
    onLotteryClick: () -> Unit,
    onRemoveAllObject: () -> Unit,
    membersList: List<String>,
    offsetList: List<OffsetData>
) {
    val openDialogMembers = remember { mutableStateOf(false) }
    val openDialogObject = remember { mutableStateOf(false) }
    val openDialogEmpty = remember { mutableStateOf(false) }
    val openDialogReset = remember { mutableStateOf(false) }
    if (openDialogMembers.value) {
        SAAlertDialog(
            title = "All members have not registered yet.",
            text = "Fill in the fields for all members",
            openDialog = openDialogMembers
        )
    }
    if (openDialogObject.value) {
        SAAlertDialog(
            title = "The number of seats does not match the number of members",
            text = "Edit the number of seats and the number of members again.",
            openDialog = openDialogObject
        )
    }
    if (openDialogEmpty.value) {
        SAAlertDialog(
            title = "No seats are assigned",
            text = "It is necessary to arrange the seats.",
            openDialog = openDialogEmpty
        )
    }
    if (openDialogReset.value) {
        SAResetDialog(
            title = "Warning",
            text = "Is it okay to delete seats?",
            openDialog = openDialogReset,
            onRemoveAllObject = onRemoveAllObject
        )
    }
    SubText(
        text = "Once you have entered all the information, please click on the lottery button." +
                " There is also a reset button.",
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )
    MainButton(
        text = "Lottery",
        color = MaterialTheme.colors.primary,
        onClick = {
            if (offsetList.isNullOrEmpty()) {
                openDialogEmpty.value = true
            } else if (membersList.contains("") || membersList.isNullOrEmpty()) {
                openDialogMembers.value = true
            } else if (membersList.size != offsetList.size || offsetList.isNullOrEmpty()) {
                openDialogObject.value = true
            } else {
                onShuffleList()
                onLotteryClick()
            }
        }
    )
    MainButton(
        text = "Reset",
        color = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary,
        borderColor = MaterialTheme.colors.primary,
        onClick = { openDialogReset.value = true }
    )
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
fun SAResetDialog(
    title: String,
    text: String,
    primaryColor: Color = MaterialTheme.colors.primary,
    onPrimaryColor: Color = contentColorFor(backgroundColor = MaterialTheme.colors.primary),
    openDialog: MutableState<Boolean>,
    onRemoveAllObject: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        backgroundColor = primaryColor,
        shape = RoundedCornerShape(10.dp),
        title = {
            Text(
                text = title,
                color = onPrimaryColor,
                fontSize = 20.sp,
                fontFamily = fontsBold
            )
        },
        text = {
            Text(
                text = text,
                fontFamily = fontsNormal,
                fontSize = 15.sp,
                color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text(
                        text = "Cancel",
                        color = onPrimaryColor,
                        fontFamily = fontsBold,
                        fontSize = 20.sp
                    )
                }
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onRemoveAllObject()
                    }
                ) {
                    Text(
                        text = "OK",
                        color = onPrimaryColor,
                        fontFamily = fontsBold,
                        fontSize = 20.sp
                    )
                }
            }
        },
        dismissButton = null
    )
}