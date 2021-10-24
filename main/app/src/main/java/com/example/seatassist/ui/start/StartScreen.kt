package com.example.seatassist.ui.start


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.R
import com.example.seatassist.ui.components.fontsBold
import com.example.seatassist.ui.components.fontsNormal
import com.google.accompanist.systemuicontroller.SystemUiController


/**
 * スタート画面
 */
@ExperimentalMaterialApi
@Composable
fun StartScreen(
    onUsageClick:() -> Unit,
    onStartClick:() -> Unit,
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
            Text(
                text = "SEAT ASSIST",
                fontSize = 60.sp,
                fontFamily = fontsBold
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
        }
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
                fontFamily = fontsNormal,
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
            fontFamily = fontsNormal,
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
