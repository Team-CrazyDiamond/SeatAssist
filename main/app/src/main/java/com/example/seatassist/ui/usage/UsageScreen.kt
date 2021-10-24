package com.example.seatassist.ui.usage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.R
import com.example.seatassist.data.UsageData
import com.example.seatassist.ui.components.SubText
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController

@ExperimentalPagerApi
@Composable
fun UsageScreen(
    onNavigationClick: () -> Unit,
    systemUiController: SystemUiController
) {
    val Sidecar = MaterialTheme.colors.primary
    val darkIcons = MaterialTheme.colors.isLight
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Sidecar,
            darkIcons = darkIcons
        )
        systemUiController.setNavigationBarColor(
            color = Sidecar,
            darkIcons = darkIcons
        )
    }
    Scaffold(
        topBar = { UsageTopBar(onNavigationClick = onNavigationClick) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
    )
    {
        UsageCardView(
            cardsList = listOf(
                UsageData("Screen1", "This cord is description of screen1.", R.drawable.usage_1),
                UsageData("Screen2", "This cord is description of screen2.", R.drawable.usage_1),
                UsageData("Screen3", "This cord is description of screen3.", R.drawable.usage_1),
                UsageData("Screen4", "This cord is description of screen4.", R.drawable.usage_1),
                UsageData("Screen5", "This cord is description of screen5.", R.drawable.usage_1)
            )
        )
    }
}

/**
 * 使い方カードをHorizontal pagerによって表示
 * @param cardsList 使い方カードの情報が入ったリスト
 */
@ExperimentalPagerApi
@Composable
fun UsageCardView(cardsList: List<UsageData>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val pagerState = rememberPagerState()

        // カードの表示
        HorizontalPager(
            count = 5,
            state = pagerState,
            itemSpacing = 20.dp,
            // 水平パディングをページの中央に追加
            contentPadding = PaddingValues(horizontal = 32.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) { page ->
            UsageCard(
                cardsList[page].name,
                cardsList[page].description,
                cardsList[page].id
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

        // TODO : ActionsRowを使えるようにする
//        ActionsRow(
//            pagerState = pagerState,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
    }
}

/**
 * 使い方のカード
 * @param cardName タイトル
 * @param description 説明文
 * @param imgId 画像の保存id
 */
@Composable
fun UsageCard(
    cardName: String,
    description: String,
    imgId: Int
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 3.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        bottom = 10.dp
                    ),
                text = cardName,
                fontSize = 30.sp
            )
            SubText(
                text = description,
                modifier = Modifier.padding(
                    bottom = 20.dp
                )
            )
            Image(
                painter = painterResource(id = imgId),
                contentDescription = "usage card $cardName",
                modifier = Modifier
                    .padding(
                        start = 32.dp,
                        end = 32.dp,
                        bottom = 16.dp
                    )
                    .clip(shape = RoundedCornerShape(1.dp))
                    .shadow(shape = CircleShape, elevation = 0.dp)
                    .size(width = 266.dp, height = 570.dp)
                    .border(BorderStroke(5.dp, MaterialTheme.colors.onPrimary))
            )
        }

    }
}

/**
 * アプリのトップバー
 * @param onNavigationClick 戻るボタンが押された時の処理
 */
@Composable
fun UsageTopBar(onNavigationClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "How to use Seat Assist") },
        navigationIcon = {
            IconButton(onClick = { onNavigationClick() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back button")
            }
        },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
    )
}