package com.frommetoyou.interchallenge.feature_marvel.domain.model.events

import com.frommetoyou.interchallenge.feature_marvel.domain.model.characters.Item

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)