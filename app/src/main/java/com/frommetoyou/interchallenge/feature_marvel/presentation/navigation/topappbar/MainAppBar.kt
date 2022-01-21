package com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.topappbar

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.states.ToolbarState
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.theme.fonts
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.theme.primaryColor

var toolbarTitle = ""

@Composable
fun MainAppBar(state: MutableState<ToolbarState>) {
    TopAppBar(
        backgroundColor = primaryColor
    ) {

        if (state.value.backIcon != null && state.value.backAction != null){
            AppBarAction(
                imageVector = state.value.backIcon!!,
                onclick = state.value.backAction!!
            )
        }
        //Title
        val paddingStart = (state.value.backIcon?.defaultWidth) ?: 0.dp
        Row(
            Modifier
                .fillMaxHeight()
                .offset(x = -paddingStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.h6) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    toolbarTitle = state.value.title
                    Crossfade(targetState = toolbarTitle) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            text = it,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppBarAction(
    imageVector: ImageVector,
    onclick: () -> Unit
) {
    IconButton(onClick = onclick) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
}