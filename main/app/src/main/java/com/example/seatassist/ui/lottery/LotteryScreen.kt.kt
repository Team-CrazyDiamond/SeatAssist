package com.example.seatassist.ui.lottery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.material.icons.filled.ReplayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.data.MembersData
import com.example.seatassist.data.OffsetData
import com.example.seatassist.ui.components.BottomBarButton
import com.example.seatassist.ui.components.DragBox
import com.example.seatassist.ui.components.fontsBold
import com.example.seatassist.ui.components.fontsNormal
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun LotteryScreen(
    screenHeight: Float,
    membersList: List<MembersData>,
    offsetList: List<OffsetData>,
    onMoveOffsetX: (Int, Float) -> Unit,
    onMoveOffsetY: (Int, Float) -> Unit,
    onShuffleList: () -> Unit,
    onNavigationClick: () -> Unit
) {
    Scaffold(
        topBar = { LotteryAppBar(title = "Lottery Result") },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        bottomBar = {
            Spacer(
                Modifier
                    .navigationBarsHeight()
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(screenHeight.dp)
            ) {
                offsetList.forEachIndexed { index, offsetData ->
                    DragBox(
                        id = offsetData.id,
                        color = offsetData.color,
                        offsetX = offsetData.offsetX.value,
                        offsetY = offsetData.offsetY.value,
                        sizeValue = offsetData.size,
                        onMoveOffsetX = onMoveOffsetX,
                        onMoveOffsetY = onMoveOffsetY
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = membersList[index].name.value,
                                textAlign = TextAlign.Center,
                                fontFamily = fontsNormal,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomBarButton(
                    text = "edit",
                    icon = Icons.Filled.ModeEditOutline,
                    contentDescription = "return button",
                    modifier = Modifier.clickable { onNavigationClick() }
                )
                Divider(
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                    modifier = Modifier
                        .height(32.dp)
                        .width(2.dp)
                )
                BottomBarButton(
                    text = "lottery",
                    icon = Icons.Filled.ChangeCircle,
                    contentDescription = "back button",
                    modifier = Modifier.clickable { onShuffleList() }
                )
            }
        }
    }
}

@Composable
fun LotteryAppBar(
    title: String,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
) {
    TopAppBar(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars,
            applyBottom = false
        ),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontFamily = fontsBold,
                color = contentColor
            )
        }
    }
}

@Composable
fun LotteryBottomBar(
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarButton(
                text = "return",
                icon = Icons.Filled.ReplayCircleFilled,
                contentDescription = "return button",
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            BottomBarButton(
                text = "lottery",
                icon = Icons.Filled.ChangeCircle,
                contentDescription = "back button",
                modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
            )
        }
    }
}