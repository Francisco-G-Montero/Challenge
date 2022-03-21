package com.frommetoyou.interchallenge.feature_marvel.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.screens.CharacterDetailScreen
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.screens.CharactersScreen
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.screens.EventsScreen
import com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.bottomnav.BottomNavItem
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.screens.CharacterDetailRouteArguments
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.states.ToolbarState
import com.frommetoyou.interchallenge.feature_marvel.presentation.viewmodel.CharactersViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NavigationGraph(
    navController: NavHostController,
    toolbarState: MutableState<ToolbarState>
) {
    val viewModel: CharactersViewModel = hiltViewModel()
    val appTitle = stringResource(id = R.string.app_name)
    NavHost(navController, startDestination = BottomNavItem.Characters.screen_route) {
        composable(BottomNavItem.Characters.screen_route) {
            toolbarState.value = toolbarState.value.copy(
                backAction = null,
                backIcon = null
            )
            toolbarState.value = toolbarState.value.copy(title = appTitle)
            CharactersScreen(navController, viewModel)
        }
        composable(BottomNavItem.Events.screen_route) {
            toolbarState.value = toolbarState.value.copy(
                backAction = null,
                backIcon = null
            )
            toolbarState.value = toolbarState.value.copy(title = appTitle)
            EventsScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = InterChallengeDestinations.CHARACTER_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(CharacterDetailRouteArguments.CHARACTER_ID_ARGUMENT) {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }
            )
        ) { entry ->
            CharacterDetailScreen(
                entry.arguments?.getString(CharacterDetailRouteArguments.CHARACTER_ID_ARGUMENT),
                viewModel,
                toolbarState,
                navController
            )
        }
    }
}