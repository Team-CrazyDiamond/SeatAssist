package com.example.seatassist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.seatassist.ui.custom.CustomScreen
import com.example.seatassist.ui.lottery.LotteryScreen
import com.example.seatassist.ui.main.MainScreen
import com.example.seatassist.ui.main.MainViewModel
import com.example.seatassist.ui.main.MainViewModelFactory
import com.example.seatassist.ui.members.MembersScreen
import com.example.seatassist.ui.members.NumberScreen
import com.example.seatassist.ui.splash.SplashScreen
import com.example.seatassist.ui.start.StartScreen
import com.example.seatassist.ui.theme.SeatAssistTheme
import com.example.seatassist.ui.usage.UsageScreen
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private var screenWidth by Delegates.notNull<Float>()
    private var screenHeight by Delegates.notNull<Float>()


    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 画面サイズを取得
        val density = resources.displayMetrics.density
        @Suppress("DEPRECATION")
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val bounds = windowManager.currentWindowMetrics.bounds
            screenWidth = bounds.width() / density - 32 // 両端のpaddingを考慮する(16 x 2)
            screenHeight = bounds.height() / density - 250

        } else {
            val defaultDisplay = windowManager.defaultDisplay
            screenWidth = defaultDisplay.width / density - 32
            screenHeight = defaultDisplay.height / density - 250
        }

        // viewModelのインスタンスを作成
        mainViewModelFactory = MainViewModelFactory(
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            context = this
        )
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = true
                    )
                    systemUiController.setNavigationBarColor(
                        color = Color.Transparent,
                        darkIcons = false
                    )
                }
                SeatAssistApp(mainViewModel = mainViewModel, screenHeight = screenHeight)
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun SeatAssistApp(mainViewModel: MainViewModel, screenHeight: Float) {
    val navController = rememberAnimatedNavController()
    SeatAssistTheme {
        SeatAssistNavHost(
            navController = navController,
            modifier = Modifier,
            mainViewModel = mainViewModel,
            screenHeight = screenHeight
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
    durationTime: Int = 500,
    easing: Easing = FastOutSlowInEasing,
    screenHeight: Float
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = SeatAssistScreen.Splash.name,
        modifier = modifier
    ) {
        composable(
            route = SeatAssistScreen.Start.name,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Main" -> slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    "Usage" -> slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Main" -> slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    "Usage" -> slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            }
        ) {
            StartScreen(
                onUsageClick = { navController.navigate(SeatAssistScreen.Usage.name) },
                onStartClick = { navController.navigate(SeatAssistScreen.Main.name) }
            )
        }
        composable(route = SeatAssistScreen.Splash.name) {
            SplashScreen(
                navController = navController
            )
        }
        composable(
            route = SeatAssistScreen.Usage.name,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Start" -> slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    "Main" -> slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Start" -> slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    "Main" -> slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    else -> null
                }
            }
        ) {
            // Usage Compose
            val previousScreen = navController.previousBackStackEntry?.destination?.route
            UsageScreen(
                onNavigationClick = {
                    if (previousScreen == "Start") {
                        navController.navigate(SeatAssistScreen.Start.name)
                    } else {
                        navController.navigate(SeatAssistScreen.Main.name)
                    }
                }
            )
        }
        composable(
            route = SeatAssistScreen.Main.name,
            // このcomposeにnavigateした際に実行するアニメーションを指定
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Members", "Custom", "Lottery" -> slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    "Start" -> slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    "Usage" -> slideInVertically(
                        initialOffsetY = { it },
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
                    "Start" -> slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
                    )
                    "Usage" -> slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = durationTime, easing = easing)
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
            )
        }
        composable(
            route = SeatAssistScreen.Members.name,
            enterTransition = { initial, _ ->
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
                onNavigationClick = { navController.navigate(SeatAssistScreen.Members.name) }
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
                screenHeight = screenHeight,
                membersList = mainViewModel.membersList,
                offsetList = mainViewModel.offsetList,
                onMoveOffsetX = mainViewModel::moveOffsetX,
                onMoveOffsetY = mainViewModel::moveOffsetY,
                onShuffleList = mainViewModel::shuffleList,
                onNavigationClick = { navController.navigate(SeatAssistScreen.Main.name) }
            )
        }
    }
}