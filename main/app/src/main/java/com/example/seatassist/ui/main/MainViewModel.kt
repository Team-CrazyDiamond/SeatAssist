package com.example.seatassist.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.seatassist.data.MembersData
import com.example.seatassist.data.OffsetData

class MainViewModel : ViewModel() {

    var numberText = mutableStateOf("")

    fun editNumber(newNumber: String) {
        numberText.value = newNumber
    }


    var offsetList = mutableStateListOf<OffsetData>()

    fun addObject(id: Int, OffsetX: Float, OffsetY: Float) {
        offsetList.add(OffsetData(id, mutableStateOf(OffsetX), mutableStateOf(OffsetY)))
    }

    fun removeObject(id: Int) {
        offsetList.removeAt(id)
        if (id != offsetList.size) {
            for (i in id until offsetList.size) {
                offsetList[i].id -= 1
            }
        }
    }

    fun moveOffsetX(id: Int, newOffsetX: Float) {
        offsetList[id].offsetX.value += newOffsetX
    }

    fun moveOffsetY(id: Int, newOffsetY: Float) {
        offsetList[id].offsetY.value += newOffsetY
    }


    var membersList = mutableStateListOf<MembersData>()
        private set

    fun addMember(id: Int, member: String) {
        membersList.add(MembersData(id, mutableStateOf(member)))
    }

    fun removeMember(id: Int) {
        membersList.removeAt(id)
        if (id != membersList.size) {
            for (i in id until membersList.size) {
                membersList[i].id -= 1
            }
        }
    }

    fun editName(id: Int, newName: String) {
        membersList[id].name.value = newName
    }
}