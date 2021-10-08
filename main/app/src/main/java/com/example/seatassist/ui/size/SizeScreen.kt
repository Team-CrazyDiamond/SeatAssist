package com.example.seatassist.ui.size

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.data.ScaleData
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun SizeScreen(
    scaleValue: ScaleData,
    sizeValue: Dp,
    onEditScale: (Float, Float) -> Unit,
    onEditSize: (Dp) -> Unit
) {
    Surface(
        color = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TransformableSample(
                        scaleValue = scaleValue,
                        onEditScale = onEditScale,
                        sizeValue = sizeValue
                    )
                }
            }


            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                for (i in 0..2) {
                    SeatCard(
                        objectSize = (50 + 25 * i).dp,
                        spacerSize = (8 + 12.5 * (2 - i)).dp,
                        scaleValue = scaleValue,
                        modifier = Modifier.padding(top = (12.5 * (2 - i)).dp),
                        onEditSize = onEditSize
                    )
                }
            }
        }
    }
}

@Composable
fun SeatCard(
    objectSize: Dp,
    spacerSize: Dp,
    scaleValue: ScaleData,
    modifier: Modifier,
    onEditSize: (Dp) -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary),
        modifier = modifier,
        elevation = 0.dp,

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(objectSize)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colors.onPrimary)
                    .clickable {
                        onEditSize(objectSize)
                        scaleValue.scale.value = 1F
                        scaleValue.rotation.value = 0F
                    }
            )
            Spacer(modifier = Modifier.size(spacerSize))
            Text(
                text = objectSize.value.toInt().toString() + ".dp",
                style = MaterialTheme.typography.h4,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TransformableSample(
    scaleValue: ScaleData,
    onEditScale: (Float, Float) -> Unit,
    sizeValue: Dp
) {
    val state = rememberTransformableState { zoomChange, _, rotationChange ->
        onEditScale(zoomChange, rotationChange)
    }
    Box(
        Modifier
            .size(sizeValue * 2)
            .graphicsLayer(
                scaleX = scaleValue.scale.value,
                scaleY = scaleValue.scale.value,
                rotationZ = scaleValue.rotation.value,

                )
            .transformable(state = state)
            .background(MaterialTheme.colors.onPrimary)
            .size(100.dp)
    )
}