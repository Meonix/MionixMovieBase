package com.nice.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nice.app_ex.base.BaseViewModel
import com.nice.app_ex.domain.base.onFailure
import com.nice.app_ex.domain.base.onLoading
import com.nice.app_ex.domain.base.onSuccess
import com.nice.myapplication.model.ListImage
import com.nice.myapplication.model.ListMovie
import com.nice.myapplication.model.Movie
import com.nice.myapplication.repo.ListImageRepo
import com.nice.myapplication.repo.ListPopularMovieRepo
import com.nice.myapplication.repo.ListUpComingMovieRepo
import com.nice.myapplication.repo.MovieRepo
import com.nice.myapplication.model.SearchMovie
import com.nice.myapplication.repo.*

class MainViewModel (private val mMovieRepo:MovieRepo,
                     private val mListPopularMovie: ListPopularMovieRepo,
                     private val mListImage: ListImageRepo,
                     private val mListUpComing: ListUpComingMovieRepo,
                     private val mSearchMovie: SearchMovieRepo): BaseViewModel(){

    private val _getDataMovieDetail = MutableLiveData<Movie>()

    val getDataMovieDetail:LiveData<Movie> get() = _getDataMovieDetail

     fun getMovie(movie_id: Int) = executeUseCase {
         mMovieRepo.getMovie(movie_id)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getDataMovieDetail.value = it
            }

            .onFailure {
                println("err $it")
            }
    }

    private val _getListPopularMovie = MutableLiveData<ListMovie>()
    val getListPopularMovie: LiveData<ListMovie> get() = _getListPopularMovie
    fun getListPopularMovie(page: Int) = executeUseCase {
        mListPopularMovie.getPoppularMovie(page)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getListPopularMovie.value = it
            }

            .onFailure {
                println("err $it")
            }
    }


    private val _getListImageMovie = MutableLiveData<ListImage>()
    val getListImageMovie: LiveData<ListImage> get() = _getListImageMovie
    fun getListImageMovie(movie_id: Int) = executeUseCase {
        mListImage.getListImage(movie_id)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getListImageMovie.value = it
            }

            .onFailure {
                println("err $it")
            }
    }
    private val _getListUpComingMovie = MutableLiveData<ListMovie>()
    val getListUpComingMovie: LiveData<ListMovie> get() = _getListUpComingMovie
    fun getListUpComingMovie(page: Int) = executeUseCase {
        mListUpComing.getUpComingMovie(page)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getListUpComingMovie.value = it
            }

            .onFailure {
                println("err $it")
            }
    }
    private val _searchMovie = MutableLiveData<SearchMovie>()
    val searchMovie: LiveData<SearchMovie> get() = _searchMovie
    fun searchMovie(query :String,page: Int) = executeUseCase {
        mSearchMovie.getUpComingMovie(query,page)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _searchMovie.value = it
            }

            .onFailure {
                println("err $it")
            }
    }
}