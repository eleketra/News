package com.example.news

import com.example.news.Model.News
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL="https://newsapi.org/"
const val API_KEY="af535bd7fc0541cab6101f2719f67f39"

interface NewsInterface {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    suspend fun getHeadlines(@Query("country")country:String, @Query("page")page:Int) : Response<News>

    //fun getHeadlines(@Query("country")country:String, @Query("page")page:Int) : Call<News>


}

object NewsService{
    val newInstance:NewsInterface
    init{
        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newInstance=retrofit.create(NewsInterface::class.java)
        }

    }
