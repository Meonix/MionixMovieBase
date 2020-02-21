package com.nice.myapplication.repo

import com.nice.app_ex.data.api.Api
import com.nice.app_ex.domain.base.Response
import com.nice.app_ex.domain.base.ResponseError
import com.nice.myapplication.model.Movie

class MovieRepo (private val mApi: Api){

    suspend fun getMovie(movie_id: Int): Response<Movie>{
//       Response.loading(null)
        return try {
            Response.success(mApi.getMovie(movie_id))
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }

}