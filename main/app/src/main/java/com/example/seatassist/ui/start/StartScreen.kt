package com.example.seatassist.ui.start


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.R
import com.example.seatassist.ui.components.SAAlertDialog
import kotlin.reflect.KProperty0

/**
 * スタート画面
 */
@ExperimentalMaterialApi
@Composable
fun StartScreen(
    onUsageClick: () -> Unit,
    onStartClick: () -> Unit
) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            var openDialog = remember {
                mutableStateOf(false)
            }
            Text(
                text = "Seat Assist",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .size(400.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ChairImg()  // 椅子の画像を挿入
                }
            }
            StartButton("Start", onStartClick)    // スタートボタン
            HowToUse("How to use this App", onUsageClick) // 使い方ボタン
            DialogTest(openDialog)
        }
    }

}

@Composable
fun DialogTest(openDialog: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .clickable(onClick = { openDialog.value = true })
    ) {
        Text(
            text = "Show toast",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h4,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
    if (openDialog.value) {
        SAAlertDialog(
            title = "title alert",
            text = "this is alert text",
            primaryColor = MaterialTheme.colors.primary,
            onPrimaryColor = MaterialTheme.colors.onPrimary,
            openDialog = openDialog
        )
    }
}

/**
 * スタートボタン
 * @param text スタートボタンの文言
 * @param onClick メインページへ
 */
@Composable
fun StartButton(text: String, onClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.h4,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

/**
 * 使い方ボタン
 * @param text How to use this App
 * @param onClick 使い方ページへ
 */
@Composable
fun HowToUse(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h4,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * 椅子の画像
 */
@Composable
fun ChairImg() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.chair_backcolor_yellow_hhd),
            contentDescription = "chair image",
            modifier = Modifier.size(500.dp)
        )
    }
}
