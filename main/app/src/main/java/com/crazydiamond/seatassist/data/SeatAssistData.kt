package com.crazydiamond.seatassist.data

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

data class MembersData(
    var id: Int,
    var name: MutableState<String>
)

data class OffsetData(
    var id: Int,
    var name: String,
    var offsetX: MutableState<Float>,
    var offsetY: MutableState<Float>,
    var color: Color,
    var size: Dp
)

data class ScaleData(
    var scale: MutableState<Float>,
    var rotation: MutableState<Float>
)

data class UsageData(
    var name: String,
    var description: String,
    var id: Int
)