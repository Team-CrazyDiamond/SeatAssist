package com.example.seatassist.data

import androidx.compose.runtime.MutableState

data class MembersData(
    var id: Int,
    var name: MutableState<String>
)

data class OffsetData(
    var id: Int,
    var offsetX: MutableState<Float>,
    var offsetY: MutableState<Float>
)