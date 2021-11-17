package com.example.seatassist.ui.custom

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.data.ScaleData
import com.example.seatassist.ui.components.*
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun CustomScreen(
    scaleValue: ScaleData,
    sizeValue: Dp,
    color: Color,
    onNavigationClick: () -> Unit,
    onEditScale: (Float, Float) -> Unit,
    onEditSize: (Dp) -> Unit,
    onEditColor: (Color) -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopBar(
                title = "Custom",
                onNavigationClick = onNavigationClick,
                backgroundColor = MaterialTheme.colors.onPrimary,
                contentColor = MaterialTheme.colors.primary
            )
        },
        bottomBar = {
            Spacer(
                Modifier
                    .navigationBarsHeight()
                    .fillMaxWidth()
            )
        },
        backgroundColor = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            val state = rememberTransformableState { zoomChange, _, rotationChange ->
                onEditScale(zoomChange, rotationChange)
            }
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.primaryVariant,
                elevation = 16.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(225.dp)
                    .transformable(state)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TransformableSample(
                        scaleValue = scaleValue,
                        sizeValue = sizeValue,
                        color = color
                    )
                }
            }
            Text(
                text = "Custom",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                fontFamily = fontsBold,
                fontSize = 34.sp,
            )
            SubText(
                text = "You can customize the sheet here",
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 16.dp
                )
            )
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                CustomMenuSize(sizeValue = sizeValue, onEditSize = onEditSize)
                CustomMenuColor(onEditColor = onEditColor, color = color)
                Spacer(modifier = Modifier.size(16.dp))
                SubText(
                    text = "When the customization is finished, click the Done button.",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                MainButton(
                    text = "Completion",
                    color = MaterialTheme.colors.primary,
                    onClick = onNavigationClick
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun CustomMenuSize(
    sizeValue: Dp,
    onEditSize: (Dp) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Size",
                fontFamily = fontsNormal,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                FloatingActionButton(
                    onClick = { onEditSize(sizeValue.minus(1.dp)) },
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    contentColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(30.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.primary,
                            shape = CircleShape
                        ),
                ) {
                    Icon(imageVector = Icons.Outlined.Remove, contentDescription = "minus button")
                }
                Text(
                    text = sizeValue.value.toInt().toString(),
                    fontFamily = fontsNormal,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(16.dp)
                )
                FloatingActionButton(
                    onClick = { onEditSize(sizeValue.plus(1.dp)) },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "plus button")
                }
            }
        }
        MainDivider()
    }
}

@ExperimentalPagerApi
@Composable
fun CustomMenuColor(
    colorList: List<Color> = listOf(
        Color(0xFFDBD2AC),
        Color(0xFF9990FF),
        Color(0xFFFF9090),
        Color(0xFFBAFFA2),
        Color(0xFFFDFF9E)
    ),
    onEditColor: (Color) -> Unit,
    color: Color,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Color",
                fontFamily = fontsNormal,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            HorizontalPager(
                count = 5,
                modifier = Modifier.width(100.dp),
                itemSpacing = 8.dp,
                contentPadding = PaddingValues(8.dp)
            ) { page ->
                IconButton(
                    onClick = {
                        onEditColor(colorList[page])

                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Circle,
                        contentDescription = "Color Icon",
                        tint = colorList[page],
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
        MainDivider()
    }
}

@Composable
fun TransformableSample(
    scaleValue: ScaleData,
    sizeValue: Dp,
    color: Color
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        backgroundColor = color,
        modifier = Modifier
            .size(sizeValue)
            .graphicsLayer(rotationZ = scaleValue.rotation.value),
        content = { }
    )
}