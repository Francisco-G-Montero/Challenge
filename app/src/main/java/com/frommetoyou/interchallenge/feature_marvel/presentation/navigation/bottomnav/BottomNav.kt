package com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.bottomnav

import androidx.compose.animation.Crossfade
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.frommetoyou.interchallenge.R

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        BottomNavItem.Characters,
        BottomNavItem.Events,
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val isSelected = currentRoute == item.screen_route
            BottomNavigationItem(
                icon = {
                    Crossfade(targetState = isSelected) { isSelected ->
                        when(isSelected){
                            true -> Icon(
                                painterResource(id =
                               item.iconSelected),
                                contentDescription = item.title,
                                tint = Color.Unspecified
                            )
                            false -> Icon(
                                painterResource(id =
                                item.icon),
                                contentDescription = item.title,
                                tint = Color.Unspecified
                            )
                        }
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp,
                        color = Black
                    )
                },
                alwaysShowLabel = true,
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}