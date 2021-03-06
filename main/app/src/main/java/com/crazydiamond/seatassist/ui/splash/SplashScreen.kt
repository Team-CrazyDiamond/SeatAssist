package com.crazydiamond.seatassist.ui.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.crazydiamond.seatassist.R
import com.crazydiamond.seatassist.SeatAssistScreen
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    val scale = remember {
        Animatable(0f)  // 最初のサイズ
    }
    LaunchedEffect(key1 = true) {   // コルーチンのキャンセルはないためkey1はtrue
        scale.animateTo(    // 拡大，縮小のアニメーション
            targetValue = 0.5f, // 最終的なサイズ
            animationSpec = tween(
                durationMillis = 500,   // アニメーションの時間
                easing = {
                    OvershootInterpolator(5f).getInterpolation(it)  // 中間のサイズ
                }
            )
        )
        delay(1800L)    // 停止時間
        navController.navigate(SeatAssistScreen.Start.name)
    }
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.systemBarsPadding()
        ) {
            Image(
                painterResource(id = R.drawable.ic_launcher_sa),
                contentDescription = "Seat Assist Log",
                modifier = Modifier.scale(scale.value)
            )
        }
    }
}