package com.example.seatassist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.seatassist.ui.custom.CustomScreen
import com.example.seatassist.ui.lottery.LotteryScreen
import com.example.seatassist.ui.main.MainScreen
import com.example.seatassist.ui.main.MainViewModel
import com.example.seatassist.ui.members.MembersScreen
import com.example.seatassist.ui.members.NumberScreen
import com.example.seatassist.ui.splash.SplashScreen
import com.example.seatassist.ui.start.StartScreen
import com.example.seatassist.ui.theme.SeatAssistTheme
import com.example.seatassist.ui.usage.UsageScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    private val mainViewModel = MainViewModel()

    @ExperimentalAnimationApi
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

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun SeatAssistApp(mainViewModel: MainViewModel) {
    val systemUiController = rememberSystemUiController()
    val navController = rememberAnimatedNavController()
    SeatAssistTheme {
        SeatAssistNavHost(
            navController = navController,
            modifier = Modifier,
            mainViewModel = mainViewModel,
            systemUiController = systemUiController
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun SeatAssistNavHost(
    navController: NavHostController,
    modifier: Modifier,
    mainViewModel: MainViewModel,
    systemUiController: SystemUiController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = SeatAssistScreen.Splash.name,
        modifier = modifier
    ) {
        composable(route = SeatAssistScreen.Start.name) {
            StartScreen(
                onUsageClick = { navController.navigate(SeatAssistScreen.Usage.name) },
                onStartClick = { navController.navigate(SeatAssistScreen.Main.name) },
                systemUiController = systemUiController
            )
        }
        composable(route = SeatAssistScreen.Splash.name) {
            SplashScreen(
                navController = navController,
                systemUiController = systemUiController
            )
        }
        composable(route = SeatAssistScreen.Usage.name) {
            // Usage Compose
            val previousScreen = navController.previousBackStackEntry?.destination?.route
            UsageScreen(
                onNavigationClick = {
                    if (previousScreen == "Start") {
                        navController.navigate(SeatAssistScreen.Start.name)
                    } else {
                        navController.navigate(SeatAssistScreen.Main.name)
                    }
                },
                systemUiController = systemUiController
            )
        }
        composable(
            route = SeatAssistScreen.Main.name,
            // このcomposeにnavigateした際に実行するアニメーションを指定
            enterTransition =  { initial, _ ->
                when (initial.destination.route) {
                    "Members", "Custom", "Lottery" -> slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(3000)
                    )
                    else -> null
                }
            },
            // このcomposeからnavigateする際に実行するアニメーション
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Members", "Custom", "Lottery" -> slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(3000)
                    )
                    else -> null
                }
            },
            popEnterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Members", "Custom", "Lottery" -> slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(3000)
                    )
                    else -> null
                }
            },
            popExitTransition = { _, target ->
                when (target.destination.route) {
                    "Members", "Custom", "Lottery" -> slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(3000)
                    )
                    else -> null
                }
            }
        ) {
            // Main Compose
            MainScreen(
                sizeValue = mainViewModel.sizeValue.value,
                scaleValue = mainViewModel.scaleValue,
                dragColor = mainViewModel.color.value,
                membersList = mainViewModel.membersList,
                offsetList = mainViewModel.offsetList,
                onAddObject = mainViewModel::addObject,
                onRemoveObject = mainViewModel::removeObject,
                onRemoveAllObject = mainViewModel::removeAllObject,
                onMoveOffsetX = mainViewModel::moveOffsetX,
                onMoveOffsetY = mainViewModel::moveOffsetY,
                onMembersClick = { navController.navigate(SeatAssistScreen.Members.name) },
                onSizeClick = { navController.navigate(SeatAssistScreen.Custom.name) },
                onShuffleList = mainViewModel::shuffleList,
                onLotteryClick = { navController.navigate(SeatAssistScreen.Lottery.name) },
                onNavigateStart = { navController.navigate(SeatAssistScreen.Start.name) },
                onNavigateUsage = { navController.navigate(SeatAssistScreen.Usage.name) },
                systemUiController = systemUiController
            )
        }
        composable(
            route = SeatAssistScreen.Members.name,
            enterTransition =  { initial, _ ->
                when (initial.destination.route) {
                    "Main" -> slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(3000)
                    )
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Main" -> slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(3000)
                    )
                    else -> null
                }
            },
            popEnterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Main" -> slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(3000)
                    )
                    else -> null
                }
            },
            popExitTransition = { _, target ->
                when (target.destination.route) {
                    "Main" -> slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(3000)
                    )
                    else -> null
                }
            }
        ) {
            // Members Compose
            MembersScreen(
                numberText = mainViewModel.numberText.value,
                numberTextNoneVisi = mainViewModel.numberTextNoneVisi.value,
                membersList = mainViewModel.membersList,
                onAddMember = mainViewModel::addMember,
                onAddMemberOne = mainViewModel::addMemberOne,
                onRemoveMember = mainViewModel::removeMember,
                onEditName = mainViewModel::editName,
                onEditNumber = mainViewModel::editNumber,
                onNavigationClick = { navController.navigate(SeatAssistScreen.Main.name) },
                onNumberNavigation = { navController.navigate(SeatAssistScreen.MembersNumber.name) },
                systemUiController = systemUiController
            )
        }
        composable(route = SeatAssistScreen.MembersNumber.name) {
            NumberScreen(
                numberText = mainViewModel.numberText.value,
                onEditNumber = mainViewModel::editNumber,
                onCompletionNumber = mainViewModel::completionNumber,
                onNoChangeMembers = mainViewModel::noChangeMembers,
                onNavigationClick = { navController.navigate(SeatAssistScreen.Members.name) },
                systemUiController = systemUiController
            )
        }
        composable(route = SeatAssistScreen.Custom.name) {
            // Custom Compose
            CustomScreen(
                scaleValue = mainViewModel.scaleValue,
                sizeValue = mainViewModel.sizeValue.value,
                color = mainViewModel.color.value,
                onEditScale = mainViewModel::editScale,
                onEditSize = mainViewModel::editSize,
                onEditColor = mainViewModel::editColor,
                onNavigationClick = { navController.navigate(SeatAssistScreen.Main.name) },
                systemUiController = systemUiController
            )
        }
        composable(route = SeatAssistScreen.Lottery.name) {
            // Lottery Compose
            LotteryScreen(
                membersList = mainViewModel.membersList,
                offsetList = mainViewModel.offsetList,
                onMoveOffsetX = mainViewModel::moveOffsetX,
                onMoveOffsetY = mainViewModel::moveOffsetY,
                onShuffleList = mainViewModel::shuffleList,
                onNavigationClick = { navController.navigate(SeatAssistScreen.Main.name) },
                systemUiController = systemUiController
            )
        }
    }
}