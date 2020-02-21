package com.nice.myapplication.model


import com.google.gson.annotations.SerializedName

data class ListImage(
    val backdrops: List<Backdrop>,
    val id: Int,
    val posters: List<Poster>
)