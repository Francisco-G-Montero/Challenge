package com.frommetoyou.interchallenge.feature_marvel.presentation.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.frommetoyou.interchallenge.R
import com.frommetoyou.interchallenge.feature_marvel.data.model.characters.Item
import com.frommetoyou.interchallenge.feature_marvel.presentation.extensions.getThumbnailPath
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.states.ToolbarState
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.theme.dividerColor
import com.frommetoyou.interchallenge.feature_marvel.presentation.viewmodel.CharactersViewModel
import com.google.accompanist.flowlayout.FlowRow

object CharacterDetailRouteArguments {
    const val CHARACTER_ID_ARGUMENT = "characterId"
}

@ExperimentalFoundationApi
@Composable
fun CharacterDetailScreen(
    characterId: String?, //No se utiliza, pero queda de ejemplo para pasar un argument desde el Navigation Graph
    viewModel: CharactersViewModel,
    toolbarState: MutableState<ToolbarState>,
    navController: NavController
) {
    val state = viewModel.state.value
    val character = state.selectedCharacter
    LazyColumn(
        Modifier.background(Color.White)
    ) {
        if (character != null) {

            toolbarState.value = toolbarState.value.copy(
                title = character.name,
                backIcon = Icons.Default.ArrowBack,
                backAction = { navController.popBackStack() }
            )
            item {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = 1.2f),
                    painter = rememberImagePainter(
                        data = character.thumbnail.getThumbnailPath()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(top = 21.dp, start = 53.dp, end = 52.dp),
                    text = character.description,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(top = 43.dp, start = 65.dp, end = 65.dp),
                    text = stringResource(id = R.string.detail_appears_title),
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
            item {
                FlowRow(
                    modifier = Modifier.padding(top = 24.dp),
                ) {
                    character.comics.items.forEach {
                        ComicItem(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ComicItem(comic: Item) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .height(88.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = comic.name,
            modifier = Modifier.padding(top = 22.dp),
            style = MaterialTheme.typography.body2,
            fontSize = 16.sp
        )
        Text(
            text = comic.name.substringAfter("(").substringBefore(")"),
            modifier = Modifier.padding(top = 8.dp),
            color = Color.Gray,
            fontSize = 14.sp
        )
        Divider(
            modifier = Modifier.padding(top = 8.dp),
            color = dividerColor,
            thickness = 1.dp
        )
    }
}