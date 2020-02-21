package com.nice.app_ex.data.api

import com.nice.myapplication.model.ListImage
import com.nice.myapplication.model.ListMovie
import com.nice.myapplication.model.Movie
import com.nice.myapplication.model.SearchMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500"
interface Api {
      @GET("movie/{movie_id}?api_key=4e2895c2da6e111db3b75e57882e7ff5&language=en-US")
        suspend fun getMovie(@Path("movie_id")movie_id: Int): Movie
//      @GET("movie/popular?api_key=4e2895c2da6e111db3b75e57882e7ff5&language=en-US&page=5")
//        suspend fun getPoppularMovie(): ListMovie
      @GET("movie/popular?api_key=4e2895c2da6e111db3b75e57882e7ff5&language=en-US")
        suspend fun getPoppularMovie(@Query("page") page: Int): ListMovie
      @GET("movie/{movie_id}/images?api_key=4e2895c2da6e111db3b75e57882e7ff5")
        suspend fun getListImage(@Path("movie_id")movie_id: Int) : ListImage
      @GET("movie/upcoming?api_key=4e2895c2da6e111db3b75e57882e7ff5&language=en-US")
        suspend fun getUpComingMovie(@Query("page") page: Int): ListMovie
      @GET("search/movie?api_key=4e2895c2da6e111db3b75e57882e7ff5&language=en-US&query=ad&page=2")
        suspend fun searchMovie(@Query("query") query: String,@Query("page") page: Int): SearchMovie
}