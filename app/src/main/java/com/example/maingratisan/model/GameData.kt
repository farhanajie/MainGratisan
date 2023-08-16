package com.example.maingratisan.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameData(
    val description: String,
    val end_date: String,
    val gamerpower_url: String,
    val id: Int,
    val image: String,
    val instructions: String,
    val open_giveaway: String,
    val open_giveaway_url: String,
    val platforms: String,
    val published_date: String,
    val status: String,
    val thumbnail: String,
    val title: String,
    val type: String,
    val users: Int,
    val worth: String
) : Parcelable {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matching = listOf(
            "$title ${platforms.replace(',', ' ')}"
        )
        return matching.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

