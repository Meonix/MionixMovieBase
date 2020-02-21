package com.nice.myapplication.repo

import com.nice.app_ex.data.api.Api
import com.nice.app_ex.domain.base.Response
import com.nice.app_ex.domain.base.ResponseError
import com.nice.myapplication.model.ListMovie

class ListUpComingMovieRepo(private val mApi: Api) {
    suspend fun getUpComingMovie(page :Int): Response<ListMovie> {
        return try {
            Response.success(mApi.getUpComingMovie(page))
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }
}