package com.ja.arviewtestapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface TopGameEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topGameEntity: TopGameEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topGames: List<TopGameEntity>)

    @Query("SELECT * FROM top_game_table")
    fun getAllTopGameEntities(): Observable<List<TopGameEntity>>
}