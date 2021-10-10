package com.example.seatassist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

/**
メニュータイトル
**/
@Composable
fun MainPlaceholder(text: String, textAlign: TextAlign = TextAlign.End, fontSize: TextUnit = 16.sp) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = textAlign,
        style = MaterialTheme.typography.h4,
        fontSize = fontSize,
        color = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.medium)
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
            border = BorderStroke(color = borderColor, width = 1.dp)
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                style = MaterialTheme.typography.h4
            )
        }
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
            Text(
                text = text,
                style = MaterialTheme.typography.h4,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            TextField(
                value = editText,
                onValueChange = { onEditText(it) },
                placeholder = { MainPlaceholder(text = placeholderText) },
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
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        style = MaterialTheme.typography.caption,
        modifier = modifier
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
            ?.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight)) ?: constraints.minHeight

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
