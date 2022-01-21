package com.frommetoyou.interchallenge.feature_marvel.data.model.events

import com.frommetoyou.interchallenge.feature_marvel.data.model.characters.Thumbnail

data class MarvelEvent(
    val characters: Characters,
    val comics: Comics,
    val creators: Creators,
    val description: String,
    val end: String,
    val id: Int,
    val modified: String,
    val next: Next,
    val previous: Previous,
    val resourceURI: String,
    val series: Series,
    val start: String,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val title: String,
    val urls: List<Url>
)