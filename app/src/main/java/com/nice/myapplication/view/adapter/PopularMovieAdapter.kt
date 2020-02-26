package com.nice.myapplication.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nice.app_ex.data.api.POSTER_BASE_URL
import com.nice.myapplication.R
import com.nice.myapplication.model.Result
import com.squareup.okhttp.OkHttpClient
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso

class PopularMovieAdapter(private val activity:Activity,
                          private val popularMovieList: MutableList<Result>,
                          val context: Context,
                          val itemClickListener: OnItemClickListener
) :RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_main_layout,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return popularMovieList.size
    }

        override fun getItemId(position: Int): Long {
            return super.getItemId(position)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listPopularMovie= popularMovieList[position]
        val moviePosterURL = POSTER_BASE_URL + listPopularMovie.posterPath

        //load image form https url into view holder (see build gradle)
        val picasso: Picasso
        val okHttpClient: OkHttpClient
        okHttpClient = OkHttpClient()
        picasso = Picasso.Builder(context)
            .downloader(OkHttpDownloader(okHttpClient))
            .build()
        picasso.load(moviePosterURL).into(holder.ivPopularMovie)
        //
            holder.bind(listPopularMovie,itemClickListener)
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
            val tvPopularMovie = itemView.findViewById(R.id.tvMovie) as TextView
            val ivPopularMovie =itemView.findViewById(R.id.ivPopularMovie) as ImageView
            val tvVoteAverage = itemView.findViewById(R.id.tvVoteAverage) as TextView


        fun bind(listPopularMovie: Result,clickListener: OnItemClickListener)
        {
            tvPopularMovie.text = listPopularMovie.originalTitle
            tvVoteAverage.text = listPopularMovie.voteAverage.toString()
//            itemView.setOnClickListener {
//                val intent = Inten    t(context, MovieDetail::class.java)
//                intent.putExtra("movie_id",listPopularMovie.id)
//                intent.putExtra("poster_path",moviePosterURL)
//                val options: ActivityOptionsCompat = ActivityOptionsCompat
//                    .makeSceneTransitionAnimation(
//                        activity,holder.ivPopularMovie
//                        ,ViewCompat.getTransitionName(holder.ivPopularMovie).toString())
//                context.startActivity(intent,options.toBundle())
//            }
            itemView.setOnClickListener {
                clickListener.onItemClicked(listPopularMovie)
            }
        }
    }

}

interface OnItemClickListener{
    fun onItemClicked(listPopularMovie: Result)
}
