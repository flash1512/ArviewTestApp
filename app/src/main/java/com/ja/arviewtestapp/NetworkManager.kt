package com.ja.arviewtestapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request =
                it.request().newBuilder()
                    .addHeader("Client-ID", "sd4grh0omdj9a31exnpikhrmsu3v46")
                    .addHeader("Accept", "application/vnd.twitchtv.v5+json")
                    .build()
            it.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl("https://api.twitch.tv")
        .build()

    val twitchApi = retrofit.create(TwitchApi::class.java)
}