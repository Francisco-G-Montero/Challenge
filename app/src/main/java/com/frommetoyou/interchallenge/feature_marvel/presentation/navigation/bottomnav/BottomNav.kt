package com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.bottomnav

import androidx.compose.animation.Crossfade
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.InterChallengeNavigationActions

@Composable
fun BottomNav(
    navigationActions: InterChallengeNavigationActions,
    currentRoute: String
) {
    val items = listOf(
        BottomNavItem.Characters,
        BottomNavItem.Events,
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.screen_route
            BottomNavigationItem(
                icon = {
                    GetNavigationIconButton(isSelected, item)
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
                    navigationActions.navigateToSpecifiedRoute(item.screen_route)
                },
            )
        }
    }
}

@Composable
private fun GetNavigationIconButton(
    isSelected: Boolean,
    item: BottomNavItem
) {
    Crossfade(targetState = isSelected) {
        when (it) {
            true -> Icon(
                painterResource(
                    id =
                    item.iconSelected
                ),
                contentDescription = item.title,
                tint = Color.Unspecified
            )
            false -> Icon(
                painterResource(
                    id =
                    item.iconUnselected
                ),
                contentDescription = item.title,
                tint = Color.Unspecified
            )
        }
    }
}