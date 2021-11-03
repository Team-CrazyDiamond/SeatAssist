package com.example.seatassist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.seatassist.ui.theme.Sidecar
import com.example.seatassist.ui.usage.UsageScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

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
    systemUiController: SystemUiController,
    primary: Color = MaterialTheme.colors.primary,
    onPrimary: Color = MaterialTheme.colors.onPrimary,
    darkIcons: Boolean = MaterialTheme.colors.isLight,
    durationTime: Int = 500,
    easing: Easing = FastOutSlowInEasing
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
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            },
            // このcomposeからnavigateする際に実行するアニメーション
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Members", "Custom", "Lottery" -> slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            }
        ) {
            val previousScreen = navController.previousBackStackEntry?.destination?.route
            if (previousScreen == "Start") {
                LaunchedEffect(key1 = true) {
                    delay(10L)
                    systemUiController.setStatusBarColor(
                        color = primary,
                        darkIcons = darkIcons
                    )
                    systemUiController.setNavigationBarColor(
                        color = onPrimary,
                        darkIcons = false
                    )
                }
            } else if (previousScreen == "Members") {
                LaunchedEffect(key1 = true) {
                    delay(1)
                    systemUiController.setNavigationBarColor(
                        color = onPrimary,
                        darkIcons = false
                    )
                }
            } else {
                LaunchedEffect(key1 = true) {
                    delay(1L)
                    systemUiController.setNavigationBarColor(
                        color = onPrimary,
                        darkIcons = false
                    )
                    delay(226L)
                    systemUiController.setStatusBarColor(
                        color = primary,
                        darkIcons = darkIcons
                    )
                }
            }
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
            )
        }
        composable(
            route = SeatAssistScreen.Members.name,
            enterTransition =  { initial, _ ->
                when (initial.destination.route) {
                    "Main" -> slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    "MembersNumber" -> slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Main" -> slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    "MembersNumber" -> slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            }
        ) {
            LaunchedEffect(key1 = true) {
                delay(1L)
                systemUiController.setNavigationBarColor(
                    color = primary,
                    darkIcons = true
                )
                systemUiController.setStatusBarColor(
                    color = primary,
                    darkIcons = darkIcons
                )
                delay(105L)
                systemUiController.setStatusBarColor(
                    color = onPrimary,
                    darkIcons = darkIcons
                )
                delay(35L)
                systemUiController.setNavigationBarColor(
                    color = onPrimary,
                    darkIcons = false
                )
                delay(223L)
                systemUiController.setStatusBarColor(
                    color = primary,
                    darkIcons = darkIcons
                )
            }
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
                onNumberNavigation = { navController.navigate(SeatAssistScreen.MembersNumber.name) }
            )
        }
        composable(
            route = SeatAssistScreen.MembersNumber.name,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Members" -> slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Members" -> slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            }
        ) {
            NumberScreen(
                numberText = mainViewModel.numberText.value,
                onEditNumber = mainViewModel::editNumber,
                onCompletionNumber = mainViewModel::completionNumber,
                onNoChangeMembers = mainViewModel::noChangeMembers,
                onNavigationClick = { navController.navigate(SeatAssistScreen.Members.name) },
                systemUiController = systemUiController
            )
        }
        composable(
            route = SeatAssistScreen.Custom.name,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Main" -> slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Main" -> slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            }
        ) {
            LaunchedEffect(key1 = true) {
                delay(120)
                systemUiController.setStatusBarColor(
                    color = onPrimary,
                    darkIcons = darkIcons
                )
            }
            // Custom Compose
            CustomScreen(
                scaleValue = mainViewModel.scaleValue,
                sizeValue = mainViewModel.sizeValue.value,
                color = mainViewModel.color.value,
                onEditScale = mainViewModel::editScale,
                onEditSize = mainViewModel::editSize,
                onEditColor = mainViewModel::editColor,
                onNavigationClick = { navController.navigate(SeatAssistScreen.Main.name) }
            )
        }
        composable(
            route = SeatAssistScreen.Lottery.name,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Main" -> slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Main" -> slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            }
        ) {
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