package com.example.seatassist.ui.lottery

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.R
import com.example.seatassist.data.MembersData
import com.example.seatassist.data.OffsetData
import com.example.seatassist.ui.components.BottomBarButton
import com.example.seatassist.ui.components.CommonTopBar
import com.example.seatassist.ui.components.DragBox
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun LotteryScreen(
    membersList: List<MembersData>,
    offsetList: List<OffsetData>,
    onMoveOffsetX: (Int, Float) -> Unit,
    onMoveOffsetY: (Int, Float) -> Unit,
    onShuffleList: () -> Unit,
    onNavigationClick: () -> Unit,
    systemUiController: SystemUiController
) {
    val pickledBlueWood = MaterialTheme.colors.onPrimary
    val darkIcons = MaterialTheme.colors.isLight
    SideEffect {
        systemUiController.setStatusBarColor(
            color = pickledBlueWood,
            darkIcons = darkIcons
        )
        systemUiController.setNavigationBarColor(
            color = pickledBlueWood,
            darkIcons = false
        )
    }
    Scaffold(
        backgroundColor = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(575.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = MaterialTheme.colors.primary)

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
                                style = MaterialTheme.typography.h4,
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