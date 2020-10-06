package com.ja.arviewtestapp

import com.google.gson.annotations.SerializedName

data class TwitchResponse(
    val top: List<TopGameInfo>
)

data class Game(
    @SerializedName("_id")
    val id: Int,
    val box: Map<String, String>,
    val name: String
)

data class TopGameInfo(
    val channels: Int,
    val viewers: Int,
    val game: Game
)
