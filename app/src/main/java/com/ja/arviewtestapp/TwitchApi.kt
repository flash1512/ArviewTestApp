package com.ja.arviewtestapp

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface TwitchApi {
    @GET("/kraken/games/top")
    fun getTopGames(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<TwitchResponse>
}