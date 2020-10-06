package com.ja.arviewtestapp

import android.content.Context
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GamesRepo(context: Context) {
    private val topGameDao = AppDatabase.getInstance(context).getTopGameEntityDao()

    fun getTopGames(isNetworkAvailable: Boolean, offset: Int, limit: Int = 20): Observable<TwitchResponse> {
        return if (isNetworkAvailable) {
            NetworkManager.twitchApi.getTopGames(limit, offset)
                .observeOn(Schedulers.io())
                .doOnNext {
                    topGameDao.insert(it.top.map { topGameInfo -> topGameInfo.mapToTopGameEntity() })
                }
        } else {
            topGameDao.getAllTopGameEntities().map {
                TwitchResponse(it.map { topGameEntity -> topGameEntity.mapToTopGameInfo() })
            }
        }
    }
}