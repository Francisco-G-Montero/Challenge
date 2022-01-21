package com.frommetoyou.interchallenge.feature_marvel.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.frommetoyou.interchallenge.feature_marvel.data.model.events.MarvelEvent
import com.frommetoyou.interchallenge.feature_marvel.presentation.extensions.getFormattedDate
import com.frommetoyou.interchallenge.feature_marvel.presentation.extensions.getThumbnailPath
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.theme.arrowDownColor
import com.frommetoyou.interchallenge.feature_marvel.presentation.ui.theme.primaryLightColor
import com.frommetoyou.interchallenge.feature_marvel.presentation.viewmodel.CharactersViewModel
import com.google.accompanist.flowlayout.FlowRow

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EventsScreen(
    navController: NavController,
    viewModel: CharactersViewModel
) {
    val state = viewModel.state.value
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
            this@Column.AnimatedVisibility(visible = state.eventList.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(2.dp),
                ) {
                    items(state.eventList) { event ->
                        EventListItem(
                            event
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
fun EventListItem(
    event: MarvelEvent,
    modifier: Modifier = Modifier,
) {
    val eventDropDownState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val constraintSet = ConstraintSet {
        val arrowDown = createRefFor("arrowDown")
        val imgThumbnail = createRefFor("imgThumbnail")
        val tvTitle = createRefFor("tvTitle")
        val tvDate = createRefFor("tvDate")

        val guideline = createGuidelineFromTop(0.141f)
        val guideline2 = createGuidelineFromTop(0.858f)
        val guideline3 = createGuidelineFromStart(0.05f)
        val guideline4 = createGuidelineFromStart(0.395f)
        val guideline5 = createGuidelineFromStart(0.3f)

        constrain(imgThumbnail) {
            start.linkTo(guideline3)
            top.linkTo(guideline)
            bottom.linkTo(guideline2)
            end.linkTo(guideline5)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
        constrain(tvTitle) {
            start.linkTo(guideline4)
            top.linkTo(guideline)
            end.linkTo(arrowDown.start)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(tvDate) {
            top.linkTo(tvTitle.bottom)
            start.linkTo(tvTitle.start)
        }
        constrain(arrowDown) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
    }
    Row(
        modifier = modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
    ) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .animateContentSize(),
            backgroundColor = Color.White,
            shape = RoundedCornerShape(3.dp),
            elevation = 18.dp
        ) {
            Column {
                ConstraintLayout(
                    constraintSet,
                    modifier = Modifier.fillMaxWidth()
                        .height(120.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .layoutId("imgThumbnail")
                            .height(86.dp),
                        painter = rememberImagePainter(
                            data = event.thumbnail.getThumbnailPath()
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.body2,
                        maxLines = 1,
                        modifier = Modifier
                            .layoutId("tvTitle")
                    )
                    Text(
                        modifier = Modifier
                            .layoutId("tvDate")
                            .padding(top = 8.dp),
                        text = event.start.getFormattedDate(),
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        onClick = { eventDropDownState.value = !eventDropDownState.value },
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .layoutId("arrowDown")
                    ) {
                        Icon(
                            imageVector =
                            if(eventDropDownState.value)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = arrowDownColor,
                            modifier = Modifier
                                .size(36.dp),
                        )
                    }
                }
                this@Column.AnimatedVisibility(visible = eventDropDownState.value) {
                    FlowRow{
                        event.comics.items.forEach {
                            ComicItem(it)
                        }
                    }
                }
            }
        }
    }
}


