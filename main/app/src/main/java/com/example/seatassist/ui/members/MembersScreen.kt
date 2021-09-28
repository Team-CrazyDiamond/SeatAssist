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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.data.MembersData
import com.example.seatassist.ui.components.MainPlaceholder
import com.example.seatassist.ui.components.MembersCustomLayout

@Composable
fun MembersScreen(
    membersList: List<MembersData>,
    numberText: String,
    onAddMember: (Int, String) -> Unit,
    onRemoveMember: (Int) -> Unit,
    onEditName: (Int, String) -> Unit
) {
    Scaffold(
        topBar = { MembersTopBar() },
        floatingActionButton = { MembersFloatingButton(id = membersList.size, onAddMember = onAddMember) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary)
            )
            MembersCustomLayout {
                val counter = remember { mutableStateOf(0) }
                if (membersList.isEmpty() && numberText.toIntOrNull() != null && counter.value == 0) {
                    repeat(times = numberText.toInt()) { onAddMember(membersList.size, "") }
                    counter.value++
                }
                membersList.forEach { member ->
                    MembersItem(
                        id = member.id,
                        name = member.name.value,
                        onEditName = onEditName,
                        onRemoveMember = onRemoveMember
                    )
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
fun MembersFloatingButton(id: Int, onAddMember: (Int, String) -> Unit) {
    FloatingActionButton(
        onClick = { onAddMember(id, "") }
    ) {
        Icon(imageVector = Icons.Outlined.Add, contentDescription = "add button")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MembersItem(
    id: Int,
    name: String,
    onEditName: (Int, String) -> Unit,
    onRemoveMember: (Int) -> Unit

) {
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
                    value = name,
                    onValueChange = { onEditName(id, it) },
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
                    onClick = { onRemoveMember(id) },
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