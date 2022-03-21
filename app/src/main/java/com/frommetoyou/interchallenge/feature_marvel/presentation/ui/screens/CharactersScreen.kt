package com.frommetoyou.interchallenge.feature_marvel.presentation.ui.screens

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.frommetoyou.interchallenge.feature_marvel.data.model.characters.MarvelCharacter
import com.frommetoyou.interchallenge.feature_marvel.presentation.extensions.getThumbnailPath
import com.frommetoyou.interchallenge.feature_marvel.presentation.navigation.InterChallengeNavigationActions
import com.frommetoyou.interchallenge.feature_marvel.presentation.viewmodel.CharactersViewModel
import java.util.*

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CharactersScreen(
    navController: NavController,
    viewModel: CharactersViewModel
) {
    val state = viewModel.state.value
    val page = viewModel.charactersPage.value
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            this@Column.AnimatedVisibility(
                visible = state.showLoading,
                modifier = Modifier.zIndex(1f)
            ) {
                CircularProgressIndicator()
            }
            this@Column.AnimatedVisibility(visible = state.characterList.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(2.dp),
                ) {
                    itemsIndexed(state.characterList) { index, character ->
                        viewModel.onChangeCharsScrollPosition(index)
                        if ((index + 1) >= page * PAGE_SIZE && !state.showLoading) {
                            viewModel.nextCharsPage()
                        }
                        CharacterListItem(
                            character,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun CharacterListItem(
    character: MarvelCharacter,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CharactersViewModel,
) {
    val navigationActions = remember(navController){
        InterChallengeNavigationActions(navController)
    }
    Row(
        modifier = modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
    ) {
        Card(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth(),
            backgroundColor = Color.White,
            shape = RoundedCornerShape(4.dp),
            onClick = {
                viewModel.setCharacterToDetail(character)
                //navController.navigate("CharacterDetailScreen")
                navigationActions.navigateToCharacterDetail()
            }
        ) {
            Row() {
                Image(
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp),
                    painter = rememberImagePainter(
                        data = character.thumbnail.getThumbnailPath()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(end = 16.dp, top = 14.dp, start = 15.dp)
                ) {
                    Text(
                        text = character.name.toUpperCase(Locale.current),
                        style = MaterialTheme.typography.body2,
                        maxLines = 1
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = character.description,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }
    }
}