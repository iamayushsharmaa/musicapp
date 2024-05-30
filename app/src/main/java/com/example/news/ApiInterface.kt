package com.example.news

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    companion object {
        const val baseUrl: String = "https://newsapi.org/v2/"
    }

    @GET("top-headlines")

    fun gettrending(
        @Query("country") country : String,
        @Query("pageSize") pagesize : Int,
        @Query("apiKey") apiKey : String,

        ) : Call<MainNewsClass>
    @GET("top-headlines")

    fun getNews(
        @Query("country") country : String,
        @Query("category") category: String,
        @Query("pageSize") pagesize : Int,
        @Query("apiKey") apiKey : String,

    ) : Call<MainNewsClass>

}