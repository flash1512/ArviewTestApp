package com.ja.arviewtestapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _topGames = MutableLiveData<List<TopGameInfo>>()
    val topGames: LiveData<List<TopGameInfo>> = _topGames

    private val gamesRepo = GamesRepo(application)
    private val limit = 20
    private var offset = 0

    fun getTopGames(isNetworkAvailable: Boolean) {
        gamesRepo.getTopGames(isNetworkAvailable, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { _topGames.postValue(it.top) }
            .subscribe()
    }

    fun loadNextPage(isNetworkAvailable: Boolean) {
        offset += limit
        gamesRepo.getTopGames(isNetworkAvailable, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                val newList: MutableList<TopGameInfo> = mutableListOf()
                newList.apply {
                    addAll(_topGames.value!!)
                    addAll(it.top)
                }
                _topGames.postValue(newList)
            }
            .subscribe()
    }
}