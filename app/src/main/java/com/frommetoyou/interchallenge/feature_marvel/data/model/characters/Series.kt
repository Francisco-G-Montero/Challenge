package com.frommetoyou.interchallenge.feature_marvel.data.model.characters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: Int
) : Parcelable