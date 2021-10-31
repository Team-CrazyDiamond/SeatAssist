package com.example.seatassist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.seatassist.R
import kotlin.math.max
import kotlin.math.roundToInt

val fontsNormal = FontFamily(
    Font(resId = R.font.timeburnernormal)
)
val fontsBold = FontFamily(
    Font(resId = R.font.timeburnerbold)
)

/**
メニュータイトル
 **/
@Composable
fun MainPlaceholder(
    text: String,
    textAlign: TextAlign = TextAlign.End,
    fontSize: TextUnit = 16.sp,
    color: Color = MaterialTheme.colors.primary
) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = textAlign,
        fontFamily = fontsNormal,
        fontSize = fontSize,
        color = color.copy(alpha = ContentAlpha.medium)
    )
}

/**
メニュー内のボタン
 **/
@Composable
fun MainButton(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    contentColor: Color = contentColorFor(backgroundColor = color),
    borderColor: Color = color,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick,
            modifier = modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = color,
                contentColor = contentColor
            ),
            border = BorderStroke(color = borderColor, width = 1.dp),
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = fontsNormal
            )
        }
    }
}

/**
抽選後画面のBottomAppBarのButton
 **/
@Composable
fun BottomBarButton(
    text: String,
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp),
            textAlign = TextAlign.Center,
            fontFamily = fontsNormal,
            fontSize = 28.sp,
        )
    }
}

/**
メニュー内の編集コンポーネント
 **/
@ExperimentalComposeUiApi
@Composable
fun MainEditText(
    text: String,
    editText: String,
    placeholderText: String,
    onEditText: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current
            Text(
                text = text,
                style = MaterialTheme.typography.h4,
                fontSize = 20.sp,
                fontFamily = fontsNormal,
                modifier = Modifier.padding(16.dp)
            )
            TextField(
                value = editText,
                onValueChange = { onEditText(it) },
                placeholder = { MainPlaceholder(text = placeholderText, fontSize = 15.sp) },
                textStyle = MaterialTheme.typography.h4.copy(
                    fontSize = 26.sp,
                    textAlign = TextAlign.End,
                    fontFamily = fontsNormal,
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
                    focusManager.clearFocus()
                })
            )
        }
        MainDivider()
    }
}

/**
メインメニューの分断ライン
 **/
@Composable
fun MainDivider() {
    Divider(
        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    )
}

/**
メニューの説明文（少し薄いやつ）
 **/
@Composable
fun SubText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        style = MaterialTheme.typography.caption,
        textAlign = textAlign,
        modifier = modifier,
        fontFamily = fontsNormal
    )
}

/**
ドラッグ可能なオブジェクト
 **/
@Composable
fun DragBox(
    id: Int,
    color: Color,
    offsetX: Float,
    offsetY: Float,
    sizeValue: Dp,
    onMoveOffsetX: (Int, Float) -> Unit,
    onMoveOffsetY: (Int, Float) -> Unit,
    onRemoveObject: (Int) -> Unit = { },
    dragBoxContent: @Composable () -> Unit
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
            },
        content = dragBoxContent
    )
}

/**
共通のTopBar
 **/
@Composable
fun CommonTopBar(
    onNavigationClick: () -> Unit,
    title: String,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor = MaterialTheme.colors.primary),
) {
    TopAppBar(
        title = { Text(
            text = title,
            fontFamily = fontsBold,
            color = contentColor
        ) },
        navigationIcon = {
            IconButton(onClick = { onNavigationClick() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back button",
                    tint = contentColor
                )
            }
        },
        elevation = 0.dp,
        backgroundColor = backgroundColor,
        contentColor = contentColor
    )
}

@Composable
fun MembersCustomLayout(
    modifier: Modifier = Modifier,
    columns: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val columnWidth = IntArray(columns) { 0 }
        val columnHeight = IntArray(columns) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            val column = index % columns
            columnWidth[column] = max(columnWidth[column], placeable.width)
            columnHeight[column] += placeable.height

            placeable
        }

        val width = columnWidth.sumOf { it }
            .coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))

        val height = columnHeight.maxOrNull()
            ?.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
            ?: constraints.minHeight

        val columnX = IntArray(columns) { 0 }
        for (i in 1 until columns) {
            columnX[i] = columnX[i - 1] + columnWidth[i - 1]
        }

        var count = 0

        layout(width, height) {
            val columnY = IntArray(columns) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val column = index % columns
                if (column == 0) count++
                placeable.placeRelative(
                    x = columnX[column] + 24 * (column + 1),
                    y = columnY[column] + 24 * count
                )
                columnY[column] += placeable.measuredHeight
            }
        }
    }
}


@Composable
fun SAAlertDialog(
    title: String,
    text: String,
    primaryColor: Color = MaterialTheme.colors.primary,
    onPrimaryColor: Color = contentColorFor(backgroundColor = MaterialTheme.colors.primary),
    openDialog: MutableState<Boolean>
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
            TextButton(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text(
                    text = "OK",
                    color = onPrimaryColor,
                    fontFamily = fontsBold,
                    fontSize = 20.sp
                )
            }
        },
        dismissButton = null
    )
}