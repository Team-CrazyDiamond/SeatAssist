package com.example.seatassist.ui.members

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.ui.components.MainPlaceholder
import com.example.seatassist.ui.main.MainViewModel
import kotlin.math.max

@Composable
fun MembersScreen(mainViewModel: MainViewModel) {
    Scaffold(
        topBar = { MembersTopBar() },
        floatingActionButton = { MembersFloatingButton(mainViewModel = mainViewModel) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary)
            )
            MembersCustomLayout {
                mainViewModel.membersList.forEachIndexed { index, member ->
//                    val name = remember { mutableStateOf(member.name) }
//                    mainViewModel.membersList[member.id].name.value = member.name.value
//                    Log.i("membersListRedraw", "$member")
                    Log.i("testIDRedraw", "${member.id}")
                    MembersItem(id = member.id, mainViewModel = mainViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MembersTopBar() {
    TopAppBar(
        title = { Text(text = "Members") },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back button")
            }
        },
        elevation = 0.dp
    )
}

@Composable
fun MembersFloatingButton(mainViewModel: MainViewModel) {
    FloatingActionButton(
        onClick = {
            val id = mainViewModel.membersList.size
//            Log.i("test", "$id")
            mainViewModel.membersList.add(MembersData(id = id, name = mutableStateOf("")))
        }
    ) {
        Icon(imageVector = Icons.Outlined.Add, contentDescription = "add button")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MembersItem(id: Int, mainViewModel: MainViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Log.i("testID", "$id")

    BoxWithConstraints {
        val itemWidth = ((with(LocalDensity.current) { constraints.maxWidth.toDp()}).value * 0.3).dp

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.width(itemWidth)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = mainViewModel.membersList[id].name.value,
                    onValueChange = { mainViewModel.onNameChange(newName = it, index = id) },
                    modifier = Modifier.weight(8F),
                    placeholder = { MainPlaceholder(text = "Input text", textAlign = TextAlign.Start, fontSize = 15.sp) },
                    textStyle = MaterialTheme.typography.h4.copy(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    ),
                    singleLine = true,
                    colors =  TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        disabledTextColor = Color.Transparent
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    })
                )
                IconButton(
                    onClick = {
//                        Log.i("membersListName", mainViewModel.membersList[id].name.value)
//                        Log.i("membersListId", "$id")
                        val test = mainViewModel.membersList.removeAt(id)
                        Log.i("testing", "$test")
                        val size = mainViewModel.membersList.size
                        Log.i("size", "$size")
                        if (id != mainViewModel.membersList.size) {
                            for (i in id until mainViewModel.membersList.size) {
                                mainViewModel.membersList[i].id -= 1
                            }
                            val list = mainViewModel.membersList.toList()
                            Log.i("list", "$list")
//                            for (i in id until mainViewModel.membersList.size - 1) {
//                                mainViewModel.membersList[i].name = mainViewModel.membersList[i + 1].name
//                            }
                        }

                    },
                    modifier = Modifier.weight(1.5F)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "delete button",
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }

            }
        }
    }
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