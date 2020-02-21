package com.nice.myapplication.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.nice.app_ex.data.api.POSTER_BASE_URL
import com.nice.myapplication.R
import com.nice.myapplication.model.Result
import com.nice.myapplication.view.MovieDetail
import com.squareup.okhttp.OkHttpClient
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso

class SearchMovieAdapter (private val activity: Activity
                          , private val SearchMovieList: MutableList<Result>
                          , val context: Context) :
    RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_main_layout,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return SearchMovieList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchMovieList= SearchMovieList[position]
        holder.tvPopularMovie.text = searchMovieList.originalTitle
        holder.tvVoteAverage.text = searchMovieList.voteAverage.toString()

        val moviePosterURL = POSTER_BASE_URL + searchMovieList.posterPath

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MovieDetail::class.java)
            intent.putExtra("movie_id",searchMovieList.id)
            intent.putExtra("poster_path",moviePosterURL)
            val options: ActivityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                    activity,holder.ivPopularMovie
                    , ViewCompat.getTransitionName(holder.ivPopularMovie).toString())

            context.startActivity(intent,options.toBundle())
        }


        //load image form https url into view holder (see build gradle)
        val picasso: Picasso
        val okHttpClient: OkHttpClient
        okHttpClient = OkHttpClient()
        picasso = Picasso.Builder(context)
            .downloader(OkHttpDownloader(okHttpClient))
            .build()
        picasso.load(moviePosterURL).into(holder.ivPopularMovie)
        //
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvPopularMovie = itemView.findViewById(R.id.tvMovie) as TextView
        val ivPopularMovie =itemView.findViewById(R.id.ivPopularMovie) as ImageView
        val tvVoteAverage = itemView.findViewById(R.id.tvVoteAverage) as TextView
    }
}