package com.frommetoyou.interchallenge.feature_marvel.presentation.extensions

import com.frommetoyou.interchallenge.feature_marvel.data.model.characters.Thumbnail
import java.text.SimpleDateFormat

fun Thumbnail.getThumbnailPath(): String {
    return "${
        this.path.replace(
            "http",
            "https"
        )
    }.${extension}"
}

fun String.getFormattedDate(): String{
    val date = SimpleDateFormat("yyyy-MM-dd").parse(this)
    val finalDate = SimpleDateFormat("dd 'de' MMMM, yyyy").format(date!!)
    return finalDate
}