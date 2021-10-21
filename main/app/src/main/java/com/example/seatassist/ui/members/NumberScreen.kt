package com.example.seatassist.ui.members

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.ui.components.CommonTopBar
import com.example.seatassist.ui.components.MainButton
import com.example.seatassist.ui.components.MainPlaceholder
import com.example.seatassist.ui.components.SubText

@ExperimentalComposeUiApi
@Composable
fun NumberScreen(
    numberText: String,
    onEditNumber: (String) -> Unit,
    onCompletionNumber: (Int) -> Unit,
    onNavigationClick: () -> Unit,
    onNoChangeMembers: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopBar(onNavigationClick = {
                onNoChangeMembers()
                onNavigationClick()
            }, title = "Number of Members")
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
    ) {
        Column {
            Text(
                text = "Please enter the number of members.",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.h6,
                fontSize = 34.sp,
                textAlign = TextAlign.Center
            )
            SubText(
                text = "TextField with the number of members will be displayed in the Members screen.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
            )
            Text(
                text = "Number of members",
                style = MaterialTheme.typography.h4,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
            NumberMemberEdit(
                numberText = numberText,
                onEditNumber = onEditNumber,
                onCompletionNumber = onCompletionNumber,
                onNavigationClick = onNavigationClick
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun NumberMemberEdit(
    numberText: String,
    onEditNumber: (String) -> Unit,
    onCompletionNumber: (Int) -> Unit,
    onNavigationClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    TextField(
        value = if (numberText == "0") "" else numberText,
        onValueChange = { onEditNumber(it) },
        placeholder = {
            MainPlaceholder(
                text = "Input number",
                fontSize = 15.sp,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Start
            )
        },
        textStyle = MaterialTheme.typography.h6.copy(fontSize = 26.sp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primaryVariant,
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
        }),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    )
    MainButton(
        text = "Change members",
        color = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary,
        onClick = {
            onCompletionNumber(numberText.toInt())
            onNavigationClick()
        }
    )
}