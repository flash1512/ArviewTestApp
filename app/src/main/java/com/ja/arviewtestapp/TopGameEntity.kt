package com.ja.arviewtestapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_game_table")
data class TopGameEntity(
    @PrimaryKey val id: Int,
    val viewers: Int,
    val channels: Int,
    val name: String,
    val imageUrl: String
)