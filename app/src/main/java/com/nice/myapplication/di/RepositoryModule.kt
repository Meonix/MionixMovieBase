package com.nice.app_ex.data.di

import com.nice.myapplication.repo.ListImageRepo
import com.nice.myapplication.repo.ListPopularMovieRepo
import com.nice.myapplication.repo.ListUpComingMovieRepo
import com.nice.myapplication.repo.MovieRepo
import com.nice.myapplication.repo.*
import org.koin.dsl.module

val repositoryModule = module {
      single { MovieRepo(get())}
      single { ListPopularMovieRepo(get())}
      single { ListImageRepo(get())}
      single { ListUpComingMovieRepo(get()) }
      single { SearchMovieRepo(get()) }
}