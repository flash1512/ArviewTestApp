package com.ja.arviewtestapp

import android.content.Context
import android.net.ConnectivityManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun RecyclerView.addEndlessScrollListener(
    linearLayoutManager: LinearLayoutManager,
    onLoadMoreFunc: (Int, Int, RecyclerView) -> Unit
): EndlessRecyclerViewScrollListener =
    object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
            onLoadMoreFunc(page, totalItemsCount, view)
        }
    }.also { addOnScrollListener(it) }

fun TopGameInfo.mapToTopGameEntity(): TopGameEntity =
    TopGameEntity(game.id, viewers, channels, game.name, game.box["medium"] ?: "")

fun TopGameEntity.mapToTopGameInfo() = TopGameInfo(
    channels, viewers, Game(id, mapOf("medium" to imageUrl), name)
)
