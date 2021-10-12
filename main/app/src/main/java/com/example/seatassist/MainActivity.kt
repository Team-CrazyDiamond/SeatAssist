package com.example.seatassist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seatassist.ui.custom.CustomScreen
import com.example.seatassist.ui.main.MainScreen
import com.example.seatassist.ui.main.MainViewModel
import com.example.seatassist.ui.members.MembersScreen
import com.example.seatassist.ui.splash.SplashScreen
import com.example.seatassist.ui.start.StartScreen
import com.example.seatassist.ui.theme.SeatAssistTheme
import com.example.seatassist.ui.usage.UsageScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    private val mainViewModel = MainViewModel()

    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeatAssistApp(mainViewModel = mainViewModel)
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun SeatAssistApp(mainViewModel: MainViewModel) {
    val pickledBlueWood = Color(0xFF324B54)    // ステータスバー，ナビゲーションバーの色
    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()
    val darkIcons = MaterialTheme.colors.isLight
    // ステータスバー，ナビゲーションバーの色を指定
    SideEffect{
        systemUiController.setSystemBarsColor(
            color = pickledBlueWood,
            darkIcons = darkIcons
        )
        systemUiController.setNavigationBarColor(
            color = pickledBlueWood,
            darkIcons = darkIcons
        )
    }

    SeatAssistTheme {
        SeatAssistNavHost(
            navController = navController,
            modifier = Modifier,
            mainViewModel = mainViewModel
        )
    }
}

@ExperimentalComposeUiApi
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
        startDestination = SeatAssistScreen.Splash.name,
        modifier = modifier
    ) {
        composable(route = SeatAssistScreen.Start.name){
            StartScreen(
                onUsageClick = { navController.navigate(SeatAssistScreen.Usage.name)},
                onStartClick = { navController.navigate(SeatAssistScreen.Main.name) }
            )
        }
        composable(route = SeatAssistScreen.Splash.name){
            SplashScreen(
                navController = navController
            )
        }
        composable(route = SeatAssistScreen.Usage.name) {
            // Usage Compose
            UsageScreen (
                onNavigationClick = { navController.navigate(SeatAssistScreen.Start.name) }
            )
        }
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
            CustomScreen(
                scaleValue = mainViewModel.scaleValue,
                sizeValue = mainViewModel.sizeValue.value,
                onEditScale = mainViewModel::editScale,
                onEditSize = mainViewModel::editSize,
                onNavigationClick = { navController.navigate(SeatAssistScreen.Main.name) }
            )
        }
    }
}