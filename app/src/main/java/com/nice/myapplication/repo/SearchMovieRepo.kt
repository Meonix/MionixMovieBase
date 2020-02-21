package com.nice.myapplication.repo

import com.nice.app_ex.data.api.Api
import com.nice.app_ex.domain.base.Response
import com.nice.app_ex.domain.base.ResponseError
import com.nice.myapplication.model.ListMovie
import com.nice.myapplication.model.SearchMovie

class SearchMovieRepo (private val mApi: Api) {
    suspend fun getUpComingMovie(query :String,page :Int): Response<SearchMovie> {
        return try {
            Response.success(mApi.searchMovie(query,page))
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }
}