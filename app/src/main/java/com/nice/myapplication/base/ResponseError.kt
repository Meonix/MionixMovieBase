package com.nice.app_ex.domain.base


import java.io.Serializable
import java.lang.RuntimeException

data class ResponseError(
    val code: Int,
    val msg: String
) : RuntimeException(), Serializable