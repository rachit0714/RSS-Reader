package com.example.rssreader.network

import retrofit2.http.GET
import com.example.rssreader.RSSFeed

interface RSSService {
    @GET("feed") // This will append to base URL
    suspend fun getFeed(): RSSFeed
}