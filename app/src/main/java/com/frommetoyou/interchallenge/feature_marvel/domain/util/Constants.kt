package com.frommetoyou.interchallenge.feature_marvel.domain.util

import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants.Companion.API_KEY
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants.Companion.PRIVATE_KEY
import com.frommetoyou.interchallenge.feature_marvel.domain.util.Constants.Companion.TIMESTAMP
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import okhttp3.internal.and

class Constants {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com"
        const val API_KEY = "444cb3f28ef1c63b179e518bb684321d"
        const val PRIVATE_KEY = "1804a41da55f293172f02e35c37cffa5f171345e"
        const val HASH = "51a3ecf2f92a23817992a2663183325e"
        const val TIMESTAMP = 1
        const val QUERY_CHARACTERS_PAGE_SIZE = 15
        const val QUERY_EVENTS_PAGE_SIZE = 25
        const val ORDER_BY_START_DATE = "-startDate"
    }
}

fun getHash(): String? {
    val timeStamp = TIMESTAMP
    val publicKey = API_KEY
    val privateKey = PRIVATE_KEY

    val messageToHash = "$timeStamp$privateKey$publicKey"
    return getMd5(messageToHash)
}

fun getMd5(input: String): String? {
    var md5Key: String? = null
    try {
        val md = MessageDigest.getInstance("MD5")
        val md5Bytes = md.digest(input.toByteArray())
        val md5 = java.lang.StringBuilder()
        for (i in md5Bytes.indices) {
            md5.append(Integer.toHexString(md5Bytes[i] and 0xFF or 0x100).substring(1, 3))
        }
        md5Key = md5.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return md5Key
}