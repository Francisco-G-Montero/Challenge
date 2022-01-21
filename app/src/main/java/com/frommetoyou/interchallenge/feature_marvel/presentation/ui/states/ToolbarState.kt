package com.frommetoyou.interchallenge.feature_marvel.presentation.ui.states

import androidx.compose.ui.graphics.vector.ImageVector

data class ToolbarState(
    val title: String = "",
    val backIcon: ImageVector? = null,
    val backAction: (() -> Unit)? = null
)