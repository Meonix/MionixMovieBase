package com.nice.myapplication.repo

import com.nice.app_ex.data.api.Api
import com.nice.app_ex.domain.base.Response
import com.nice.app_ex.domain.base.ResponseError
import com.nice.myapplication.model.ListImage

class ListImageRepo (private val mApi: Api) {
    suspend fun getListImage(movie_id: Int): Response<ListImage> {
        return try {
            Response.success(mApi.getListImage(movie_id))
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }
}