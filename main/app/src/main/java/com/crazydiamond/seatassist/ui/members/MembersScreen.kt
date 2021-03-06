package com.crazydiamond.seatassist.ui.members

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.PersonAddAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crazydiamond.seatassist.data.MembersData
import com.crazydiamond.seatassist.ui.components.*
import com.google.accompanist.insets.systemBarsPadding

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun MembersScreen(
    membersList: List<MembersData>,
    numberText: String,
    numberTextNoneVisi: Int,
    onAddMember: (Int, String) -> Unit,
    onAddMemberOne: (Int, String) -> Unit,
    onRemoveMember: (Int) -> Unit,
    onEditName: (Int, String) -> Unit,
    onEditNumber: (String) -> Unit,
    onNavigationClick: () -> Unit,
    onNumberNavigation: () -> Unit
) {
    BackdropScaffold(
        appBar = {
            MembersTopBar(
                id = membersList.size,
                onAddMemberOne = onAddMemberOne,
                onNavigationClick = onNavigationClick
            )
        },
        backLayerContent = {
            if (membersList.isEmpty() && numberText.toIntOrNull() != null) {
                repeat(numberTextNoneVisi) { onAddMember(membersList.size, "") }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                MembersCustomLayout {
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
        },
        frontLayerContent = {
            MembersNumber(
                numberTextNoneVisi = numberTextNoneVisi.toString(),
                onEditNumber = onEditNumber,
                onNavigationClick = onNavigationClick,
                onNumberNavigation = onNumberNavigation
            )
        },
        peekHeight = 420.dp,
        headerHeight = 110.dp,
        backLayerBackgroundColor = MaterialTheme.colors.primary,
        backLayerContentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary),
        frontLayerBackgroundColor = MaterialTheme.colors.onPrimary,
        frontLayerContentColor = MaterialTheme.colors.primary,
        frontLayerScrimColor = Color.Unspecified,
        frontLayerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        frontLayerElevation = 8.dp,
        modifier = Modifier.systemBarsPadding()
    )
}

@Composable
fun MembersTopBar(id: Int, onAddMemberOne: (Int, String) -> Unit, onNavigationClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Members",
                color = MaterialTheme.colors.onPrimary,
                fontSize = 20.sp,
                fontFamily = fontsBold
            )
        },
        navigationIcon = {
            IconButton(onClick = { onNavigationClick() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = { onAddMemberOne(id, "") }) {
                Icon(
                    imageVector = Icons.Outlined.PersonAddAlt,
                    contentDescription = "member add button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
    )
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
    val focusManager = LocalFocusManager.current

    BoxWithConstraints {
        val itemWidth =
            ((with(LocalDensity.current) { constraints.maxWidth.toDp() }).value * 0.3).dp

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary,
            elevation = 0.dp,
            modifier = Modifier.width(itemWidth)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = name,
                    onValueChange = { onEditName(id, it) },
                    modifier = Modifier.weight(8F),
                    placeholder = {
                        MainPlaceholder(
                            text = "Input text",
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = fontsNormal
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        disabledTextColor = Color.Transparent
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    })
                )
                IconButton(
                    onClick = { onRemoveMember(id) },
                    modifier = Modifier.weight(1.5F)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "delete button",
                        tint = MaterialTheme.colors.primary,
                    )
                }

            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun MembersNumber(
    numberTextNoneVisi: String,
    onEditNumber: (String) -> Unit,
    onNavigationClick: () -> Unit,
    onNumberNavigation: () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Members",
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            fontFamily = fontsBold,
            fontSize = 34.sp,
        )
        SubText(
            text = "You can decide the number of members and their names here",
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 16.dp
            )
        )
        MembersNumberItem(
            numberTextNoneVisi = numberTextNoneVisi,
            onEditNumber = onEditNumber,
            onNumberNavigation = onNumberNavigation
        )
        MainButton(
            text = "Completion",
            color = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            borderColor = MaterialTheme.colors.onPrimary,
            onClick = { onNavigationClick() }
        )
    }
}

@Composable
fun MembersNumberItem(
    numberTextNoneVisi: String,
    onEditNumber: (String) -> Unit,
    onNumberNavigation: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEditNumber(numberTextNoneVisi)
                onNumberNavigation()
            }
    ) {
        Text(
            text = "Number of members",
            fontFamily = fontsNormal,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = numberTextNoneVisi,
                fontFamily = fontsNormal,
                color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = "number of members",
                tint = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
            )
        }
    }
}