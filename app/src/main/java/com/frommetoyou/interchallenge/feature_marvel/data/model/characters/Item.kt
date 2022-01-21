package com.frommetoyou.interchallenge.feature_marvel.data.model.characters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val name: String,
    val resourceURI: String
): Parcelable