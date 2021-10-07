package com.example.seatassist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seatassist.ui.main.MainScreen
import com.example.seatassist.ui.main.MainViewModel
import com.example.seatassist.ui.members.MembersScreen
import com.example.seatassist.ui.size.SizeScreen
import com.example.seatassist.ui.theme.SeatAssistTheme
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {

    private val mainViewModel = MainViewModel()

    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeatAssistApp(mainViewModel = mainViewModel)
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun SeatAssistApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    SeatAssistTheme {
        SeatAssistNavHost(
            navController = navController,
            modifier = Modifier,
            mainViewModel = mainViewModel
        )
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun SeatAssistNavHost(
    navController: NavHostController,
    modifier: Modifier,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = SeatAssistScreen.Main.name,
        modifier = modifier
    ) {
        composable(route = SeatAssistScreen.Main.name) {
            // Main Compose
            MainScreen(
                numberText = mainViewModel.numberText.value,
                sizeValue = mainViewModel.sizeValue.value,
                scaleValue = mainViewModel.scaleValue,
                dragColor = mainViewModel.color.value,
                offsetList = mainViewModel.offsetList,
                onAddObject = mainViewModel::addObject,
                onRemoveObject = mainViewModel::removeObject,
                onMoveOffsetX = mainViewModel::moveOffsetX,
                onMoveOffsetY = mainViewModel::moveOffsetY,
                onMembersClick = { navController.navigate(SeatAssistScreen.Members.name) },
                onSizeClick = { navController.navigate(SeatAssistScreen.Size.name) },
                onEditNumber = mainViewModel::editNumber

            )
        }
        composable(route = SeatAssistScreen.Members.name) {
            // Members Compose
            MembersScreen(
                numberText = mainViewModel.numberText.value,
                membersList = mainViewModel.membersList,
                onAddMember = mainViewModel::addMember,
                onRemoveMember = mainViewModel::removeMember,
                onEditName = mainViewModel::editName,
                onNavigationClick = { navController.navigate(SeatAssistScreen.Main.name) }
            )
        }
        composable(route = SeatAssistScreen.Size.name) {
            // Size Compose
            SizeScreen(
                scaleValue = mainViewModel.scaleValue,
                sizeValue = mainViewModel.sizeValue.value,
                onEditScale = mainViewModel::editScale,
                onEditSize = mainViewModel::editSize
            )
        }
    }
}