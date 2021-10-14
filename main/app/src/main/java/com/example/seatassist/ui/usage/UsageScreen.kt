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

@ExperimentalPagerApi
@Composable
fun UsageScreen(
    onNavigationClick: () -> Unit
) {
    Scaffold(
        topBar = { UsageTopBar(onNavigationClick = onNavigationClick) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
    )

    {
        UsageCardsList(
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
fun UsageCardsList(
    cardsList: List<UsageData>
) {
    Column {
        Row(
            modifier = Modifier.padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalPager(
                count = 5,
                itemSpacing = 10.dp
            ) { page ->
                UsageCard(
                    cardsList[page].name,
                    cardsList[page].description,
                    cardsList[page].id
                )
            }
        }
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
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
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
                    .shadow(shape = CircleShape, elevation = 0.dp)
                    .size(width = 259.dp, height = 555.dp)
                    .clip(shape = RoundedCornerShape(1.dp))
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