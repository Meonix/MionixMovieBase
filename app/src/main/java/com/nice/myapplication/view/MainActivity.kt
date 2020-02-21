package com.nice.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nice.myapplication.viewModel.MainViewModel
import com.nice.myapplication.R
import com.nice.myapplication.model.Result
import com.nice.myapplication.view.adapter.PopularMovieAdapter
import com.nice.myapplication.view.adapter.UpComingMovieAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private var pagePopular = 1
    private var pageUpcoming = 1
    private var popularIsLoading = true
    private var upComingIsLoading = true
    private lateinit var ibSearch : ImageButton
    private lateinit var progressBar : ProgressBar
    private val myViewModel : MainViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewUpcoming: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var linearLayoutManager1: LinearLayoutManager

    private lateinit var adapterPopularMovieView : PopularMovieAdapter
    private lateinit var adapterUpcomingMovieView : UpComingMovieAdapter


    private val listPopularMovie :MutableList<Result> = mutableListOf()
    private val listUpcomingMovie :MutableList<Result> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        ibSearch.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
        setupViewModel()
    }
    private fun initView(){
        recyclerView = findViewById(R.id.rvPopularMovie)
        ibSearch = findViewById(R.id.ibSearch)
        recyclerViewUpcoming = findViewById(R.id.rvUpcomingMovie)
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager1 = LinearLayoutManager(this)
        linearLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.isNestedScrollingEnabled = true
        recyclerView.setHasFixedSize(true)

        recyclerViewUpcoming.layoutManager = linearLayoutManager1
        recyclerViewUpcoming.isNestedScrollingEnabled = true
        recyclerViewUpcoming.setHasFixedSize(true)

        progressBar = findViewById(R.id.mainProgressBar)
    }
    private fun setupViewModel() {
        // Setup Trending Movie Recycle View
        myViewModel.getListPopularMovie(pagePopular)
        myViewModel.getListPopularMovie.observe(this, Observer {

            listPopularMovie.addAll(it.results)
            recyclerView.adapter!!.notifyDataSetChanged()
        })
        adapterPopularMovieView = PopularMovieAdapter(this@MainActivity,listPopularMovie, this)

        recyclerView.adapter = adapterPopularMovieView


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dx > 0) {
                val visibleItemCount = linearLayoutManager.childCount
                val pastVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapterPopularMovieView.itemCount
                if (popularIsLoading) {
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        pagePopular += 1

                        getPopularPage()
                        popularIsLoading = false
                    }
                }
//              }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        // Setup Upcoming Movie Recycle View

        myViewModel.getListUpComingMovie(pageUpcoming)
        myViewModel.getListUpComingMovie.observe(this, Observer {
            listUpcomingMovie.addAll(it.results)
            recyclerViewUpcoming.adapter!!.notifyDataSetChanged()
        })
        adapterUpcomingMovieView = UpComingMovieAdapter(this@MainActivity,listUpcomingMovie, this)
        recyclerViewUpcoming.adapter = adapterUpcomingMovieView

        recyclerViewUpcoming.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dx > 0) {
                val visibleItemCount = linearLayoutManager1.childCount
                val pastVisibleItem = linearLayoutManager1.findFirstCompletelyVisibleItemPosition()
                val total = adapterUpcomingMovieView.itemCount
                if (upComingIsLoading) {
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        pagePopular += 1
                        getUpcomingPage()
                        upComingIsLoading = false
                    }
                }
//              }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    fun getPopularPage() {
        popularIsLoading = true
        progressBar.visibility = View.VISIBLE
        Handler().postDelayed({
                myViewModel.getListPopularMovie(pagePopular)
            progressBar.visibility = View.GONE

            popularIsLoading = true
        },1200)
    }

    fun getUpcomingPage() {
        upComingIsLoading = true
        mainProgressBarUpcoming.visibility = View.VISIBLE
        Handler().postDelayed({
            myViewModel.getListUpComingMovie(pageUpcoming)
            mainProgressBarUpcoming.visibility = View.GONE

            upComingIsLoading = true
        }, 1200)
    }
}
