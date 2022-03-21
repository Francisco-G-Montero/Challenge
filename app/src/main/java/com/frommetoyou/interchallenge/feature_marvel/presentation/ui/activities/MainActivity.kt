package com.frommetoyou.interchallenge.feature_marvel.presentation.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.InterChallengeNavigationActions
import com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.topappbar.MainAppBar
import com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.bottomnav.BottomNav
import com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.NavigationGraph
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.states.ToolbarState
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.theme.InterChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {

            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun MyApp(content: @Composable () -> Unit) {
    InterChallengeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            val toolbarState: MutableState<ToolbarState> = remember {
                mutableStateOf(ToolbarState())
            }
            val navigationActions = remember(navController) {
                InterChallengeNavigationActions(navController)
            }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: ""
            Scaffold(
                topBar = { MainAppBar(toolbarState) },
                bottomBar = {
                    BottomNav(
                        navigationActions = navigationActions,
                        currentRoute = currentRoute
                    )
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    NavigationGraph(navController = navController, toolbarState = toolbarState)
                }
                content()
            }
        }
    }
}