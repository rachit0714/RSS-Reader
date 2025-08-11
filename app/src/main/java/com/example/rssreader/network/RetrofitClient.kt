package com.example.rssreader.network

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://rachitcsc290.wordpress.com/" // Change to your blog base URL

    val rssService: RSSService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(RSSService::class.java)
    }
}