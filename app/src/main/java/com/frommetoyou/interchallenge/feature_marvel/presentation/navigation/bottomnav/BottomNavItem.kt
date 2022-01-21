package com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.bottomnav

import com.frommetoyou.interchallenge.R

sealed class BottomNavItem(
    var title: String,
    var icon: Int,
    var iconSelected: Int,
    var screen_route: String
) {
    object Characters : BottomNavItem(
        "Characters",
        R.drawable.ic_superhero_disabled,
        R.drawable.ic_superhero_enabled,
        "CharactersScreen"
    )

    object Events : BottomNavItem(
        "Events",
        R.drawable.ic_calendar_disabled,
        R.drawable.ic_calendar_enabled,
        "EventsScreen"
    )
}