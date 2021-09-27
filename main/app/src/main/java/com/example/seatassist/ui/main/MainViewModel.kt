package com.example.seatassist.ui.main

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.seatassist.ui.members.MembersData

class MainViewModel: ViewModel() {

    // number of seats
    var numberText by mutableStateOf("")

    fun onNameChange(newName: String, index: Int) {
        membersList[index].name.value = newName
    }

    var membersList = mutableStateListOf<MembersData>()
}