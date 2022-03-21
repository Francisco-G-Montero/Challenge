package com.frommetoyou.interchallenge.feature_marvel.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Destinations used in the [InterChallengeApp].
 */
object InterChallengeDestinations {
    const val CHARACTER_DETAIL_ROUTE = "CharacterDetailScreen"
    const val CHARACTERS_ROUTE = "CharactersScreen"
    const val EVENTS_ROUTE = "EventsScreen"
}

/**
 * Models the navigation actions in the app.
 */
class InterChallengeNavigationActions(navController: NavController) {
    val navigateToCharacterDetail: () -> Unit = {
        navController.navigate(InterChallengeDestinations.CHARACTER_DETAIL_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToEvents: () -> Unit = {
        navController.navigate(InterChallengeDestinations.EVENTS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToCharacters: () -> Unit = {
        navController.navigate(InterChallengeDestinations.CHARACTERS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSpecifiedRoute: (route: String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}