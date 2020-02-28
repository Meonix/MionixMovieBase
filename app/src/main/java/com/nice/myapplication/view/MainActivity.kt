package com.nice.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.nice.app_ex.data.api.POSTER_BASE_URL
import com.nice.myapplication.viewModel.MainViewModel
import com.nice.myapplication.R
import com.nice.myapplication.model.Result
import com.nice.myapplication.repo.ListPopularMovieRepo
import com.nice.myapplication.view.adapter.OnItemClickListener
import com.nice.myapplication.view.adapter.PopularMovieAdapter
import com.nice.myapplication.view.adapter.UpComingMovieAdapter
import com.squareup.okhttp.OkHttpClient
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_main_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() , OnItemClickListener {
    private var mAuth: FirebaseAuth? = null
    private var UsersRef: DatabaseReference? = null
    private var currentUser: FirebaseUser? = null
    private var pagePopular = 1
    private var pageUpcoming = 1
    private var popularIsLoading = true
    private var upComingIsLoading = true
    private lateinit var progressBar : ProgressBar
    private val myViewModel : MainViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewUpcoming: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var linearLayoutManager1: LinearLayoutManager

    private lateinit var adapterPopularMovieView : PopularMovieAdapter
    private lateinit var adapterUpcomingMovieView : UpComingMovieAdapter
    private lateinit var toolbar : Toolbar

    private val listPopularMovie :MutableList<Result> = mutableListOf()
    private val listUpcomingMovie :MutableList<Result> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setupViewModel()
    }
    private fun initView(){
        recyclerView = findViewById(R.id.rvPopularMovie)
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
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)


    }
    private fun setupViewModel() {
        // Setup Trending Movie Recycle View
        myViewModel.getListPopularMovie(pagePopular)
        myViewModel.getListPopularMovie.observe(this, Observer {

            listPopularMovie.addAll(it.results)
            recyclerView.adapter!!.notifyDataSetChanged()
        })
        adapterPopularMovieView = PopularMovieAdapter(this@MainActivity,listPopularMovie, this,this)

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
        adapterUpcomingMovieView = UpComingMovieAdapter(this@MainActivity,listUpcomingMovie, this,this)
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.option_menu_main, menu)
        if (currentUser != null) {
            menu.findItem(R.id.login).title = "Profile"
        } else {
            menu.findItem(R.id.login).title = "Login"
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if(item.itemId == R.id.search){
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
        if(item.itemId == R.id.login){
            if(item.title == "Login") {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            else if(item.title == "Profile"){
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
    override fun onItemClicked(listPopularMovie: Result) {
        val moviePosterURL = POSTER_BASE_URL + listPopularMovie.posterPath
        val intent = Intent(this@MainActivity, MovieDetail::class.java)
                intent.putExtra("movie_id",listPopularMovie.id)
                intent.putExtra("poster_path",moviePosterURL)
                val options: ActivityOptionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                        this@MainActivity,ivPopularMovie
                        , ViewCompat.getTransitionName(ivPopularMovie).toString())
        startActivity(intent,options.toBundle())
    }

    override fun onStart() {
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser
        super.onStart()
    }
}
