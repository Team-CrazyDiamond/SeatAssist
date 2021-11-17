package com.example.seatassist.ui.start

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.seatassist.R
import com.example.seatassist.ui.components.fontsBold
import com.example.seatassist.ui.components.fontsNormal
import com.google.accompanist.insets.navigationBarsHeight


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
        bottomBar = { Spacer(
            Modifier
                .navigationBarsHeight()
                .fillMaxWidth()) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(MaterialTheme.colors.primary)
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = "SEAT ASSIST",
                fontSize = 60.sp,
                fontFamily = fontsBold
            )
            BoxWithConstraints {
                val boxWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
                Box(
                    modifier = Modifier
                        .size(boxWidth)
                ) {
                    LottieLoader()
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
        shape = RoundedCornerShape(40.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = text,
                fontFamily = fontsNormal,
                fontSize = 50.sp,
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp, start = 50.dp, end = 50.dp)
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
 * 椅子アニメーション
 */
@Composable
fun LottieLoader() {
    val compositionResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.startmovie
        )
    )
    val progress by animateLottieCompositionAsState(
        composition = compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )

    LottieAnimation(
        compositionResult.value,
        progress,
        modifier = Modifier
            .padding(top = 30.dp, bottom = 50.dp, start = 40.dp, end = 40.dp)
            .clip(CircleShape)
            .border(
                width = 4.dp,
                color = MaterialTheme.colors.onPrimary,
                shape = CircleShape
            )
    )
}
