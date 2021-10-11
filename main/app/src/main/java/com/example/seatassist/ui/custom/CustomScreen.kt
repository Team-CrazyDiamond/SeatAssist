package com.example.seatassist.ui.custom

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seatassist.data.ScaleData
import com.example.seatassist.ui.components.MainDivider
import com.example.seatassist.ui.components.SubText
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun CustomScreen(
    scaleValue: ScaleData,
    sizeValue: Dp,
    onNavigationClick: () -> Unit,
    onEditScale: (Float, Float) -> Unit,
    onEditSize: (Dp) -> Unit
) {
    Scaffold(
        topBar = { CustomTopBar(onNavigationClick = onNavigationClick) },
        backgroundColor = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
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
                    .height(300.dp)
                    .transformable(state)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TransformableSample(
                        scaleValue = scaleValue,
                        sizeValue = sizeValue
                    )
                }
            }

            // ここにカスタムメニューを書く
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Custom",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    style = MaterialTheme.typography.h6,
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
                CustomMenuSize(sizeValue = sizeValue, onEditSize = onEditSize)
                CustomMenuItem(text = "Color", onClick = { })
            }
        }
    }
}

@Composable
fun CustomTopBar(onNavigationClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Custom") },
        navigationIcon = {
            IconButton(onClick = { onNavigationClick() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back button")
            }
        },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary
    )
}

@Composable
fun CustomMenuItem(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h4,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        MainDivider()
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
                style = MaterialTheme.typography.h4,
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
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Remove, contentDescription = "minus button")
                }
                Text(
                    text = sizeValue.value.toInt().toString(),
                    style = MaterialTheme.typography.h4,
                    fontSize = 20.sp,
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

@Composable
fun TransformableSample(
    scaleValue: ScaleData,
    sizeValue: Dp
) {
    Box(
        Modifier
            .size(sizeValue)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
            .clip(shape = RoundedCornerShape(8.dp))
            .graphicsLayer(rotationZ = scaleValue.rotation.value)
            .background(MaterialTheme.colors.primaryVariant)
    )
}