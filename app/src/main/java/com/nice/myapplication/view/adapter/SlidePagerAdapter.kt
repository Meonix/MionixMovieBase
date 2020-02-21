package com.nice.myapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.okhttp.OkHttpClient
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import com.nice.app_ex.data.api.POSTER_BASE_URL
import com.nice.myapplication.R
import com.nice.myapplication.model.Backdrop


class SlidePagerAdapter(context: Context,list: MutableList<Backdrop>) : PagerAdapter() {
    var mContext:Context = context
    var mList:MutableList<Backdrop> = list

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var layoutInflater:LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val slideLayout:View = layoutInflater.inflate(R.layout.slide_item,null)
        var slideImage:ImageView = slideLayout.findViewById(R.id.ivSlideItem)
        val picasso: Picasso
        val okHttpClient: OkHttpClient
        okHttpClient = OkHttpClient()
        picasso = Picasso.Builder(mContext)
            .downloader(OkHttpDownloader(okHttpClient))
            .build()
        picasso.load(POSTER_BASE_URL + mList[position].filePath).into(slideImage)


        container.addView(slideLayout)
        return slideLayout
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
    override fun getCount(): Int {
        return mList.count()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}