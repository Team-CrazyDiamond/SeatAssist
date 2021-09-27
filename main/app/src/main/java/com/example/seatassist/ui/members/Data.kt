package com.example.seatassist.ui.members

import androidx.compose.runtime.MutableState

data class MembersData(
    var id: Int,
    var name: MutableState<String>
)