package com.nice.myapplication.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.nice.myapplication.R
import com.nice.myapplication.view.adapter.SlidePagerAdapter
import com.nice.myapplication.model.Backdrop
import com.nice.myapplication.viewModel.MainViewModel
import com.squareup.okhttp.OkHttpClient
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.fixedRateTimer

class MovieDetail : AppCompatActivity() {
    private val myViewModel : MainViewModel by viewModel()
    private lateinit var iv : ImageView
    private lateinit var tb : Toolbar
    private lateinit var cl : CollapsingToolbarLayout
    private lateinit var tvAbout : TextView
    private lateinit var poster_path:String
    private var listSlides :MutableList<Backdrop> = ArrayList()
    internal lateinit var slidePager: ViewPager
    private var slidePagerAdapter: SlidePagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        initViewMovieDetail()
        val movie_id:String = intent.extras!!["movie_id"].toString()
        poster_path= intent.extras!!["poster_path"].toString()
        setupViewModel(movie_id)


    }
    @SuppressLint("ResourceAsColor")
    private fun initViewMovieDetail(){
        tb = findViewById(R.id.toolbarMovieDetail)
        cl = findViewById(R.id.ctMovieDetail)
        tvAbout = findViewById(R.id.tvAboutTheMovie)
        setSupportActionBar(tb)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        slidePager = findViewById(R.id.vpMovieDetail)
        iv = findViewById(R.id.ivMovieDetail)

    }
    private fun setupViewModel(movie_id : String){
        myViewModel.getMovie(movie_id.toInt())
        myViewModel.getDataMovieDetail.observe(this, Observer {
            tvAbout.text = it.overview
            cl.title = it.title
            val picasso: Picasso
            val okHttpClient: OkHttpClient
            okHttpClient = OkHttpClient()
            picasso = Picasso.Builder(applicationContext)
                .downloader(OkHttpDownloader(okHttpClient))
                .build()
            picasso.load(poster_path).into(iv)

        })
        myViewModel.getListImageMovie(movie_id.toInt())
        myViewModel.getListImageMovie.observe(this, Observer {
            listSlides.addAll(it.backdrops)
            slidePagerAdapter =
                SlidePagerAdapter(this, listSlides)
            slidePager.adapter = slidePagerAdapter

            tlMovieDetail.setupWithViewPager(slidePager,true)
            fixedRateTimer(name = "timer",initialDelay = 4000,period = 6000){
                runOnUiThread {
                    if(slidePager.currentItem < listSlides.count() - 1){
                        slidePager.currentItem = slidePager.currentItem+1
                    }
                    else {
                        slidePager.currentItem = 0
                    }
                }
            }
        })

    }
}
