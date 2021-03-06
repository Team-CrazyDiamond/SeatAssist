package com.crazydiamond.seatassist.ui.members

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crazydiamond.seatassist.ui.components.*
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues

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
            NumberTopBar(
                onNavigationClick = {
                    onNoChangeMembers()
                    onNavigationClick()
                }
            )
        },
        bottomBar = {
            Spacer(
                Modifier
                    .navigationBarsHeight()
                    .fillMaxWidth())
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            Text(
                text = "Please enter the number of members.",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                fontFamily = fontsBold,
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
                fontFamily = fontsNormal,
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

@Composable
fun NumberTopBar(
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor = MaterialTheme.colors.primary),
    onNavigationClick: () -> Unit
) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars,
            applyBottom = false
        )
    ) {
        TextButton(onClick = { onNavigationClick() }) {
            Text(
                text = "CLOSE",
                fontFamily = fontsBold,
                fontSize = 18.sp,
                color = contentColor
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
    val openDialogNumber = remember { mutableStateOf(false) }
    if (openDialogNumber.value) {
        SAWarningDialog(
            title = "Warning",
            text = "Is it okay that the members I have entered will be reset?",
            numberText = numberText,
            openDialog = openDialogNumber,
            onCompletionNumber = onCompletionNumber,
            onEditNumber = onEditNumber,
            onNavigationClick = onNavigationClick
        )
    }
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
        textStyle = TextStyle(
            fontFamily = fontsNormal,
            fontSize = 26.sp
        ),
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
        onClick = { openDialogNumber.value = true }
    )
}

@Composable
fun SAWarningDialog(
    title: String,
    text: String,
    numberText: String,
    primaryColor: Color = MaterialTheme.colors.primary,
    onPrimaryColor: Color = contentColorFor(backgroundColor = MaterialTheme.colors.primary),
    openDialog: MutableState<Boolean>,
    onCompletionNumber: (Int) -> Unit,
    onEditNumber: (String) -> Unit,
    onNavigationClick: () -> Unit
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
                        println(numberText)
                        if (numberText.isEmpty()) {
                            onEditNumber("0")
                            onCompletionNumber(0)
                        } else {
                            onCompletionNumber(numberText.toInt())
                        }
                        onNavigationClick()
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