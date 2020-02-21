package com.nice.myapplication.view.fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.nice.myapplication.R
import com.nice.myapplication.model.Result
import com.nice.myapplication.view.SearchActivity
import com.nice.myapplication.view.adapter.SearchMovieAdapter
import com.nice.myapplication.viewModel.MainViewModel
import com.nice.myapplication.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.RuntimeException

/**
 * A simple [Fragment] subclass.
 */
class SearchMovieFragment : Fragment() {
    private lateinit var rvSearchMovie : RecyclerView
    private val listSearchMovie :MutableList<Result> = mutableListOf()
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var searchMovieAdapter:SearchMovieAdapter
    private val myViewModel : MainViewModel by viewModel()
    private var pageMovie = 1
    private lateinit var SearchMovieView : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        SearchMovieView = inflater.inflate(R.layout.fragment_search_movie, container, false)
        rvSearchMovie = SearchMovieView.findViewById(R.id.rvFragmentSearchMovie)

        gridLayoutManager = GridLayoutManager(context,3)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL

        rvSearchMovie.layoutManager = gridLayoutManager
        rvSearchMovie.isNestedScrollingEnabled = true
        rvSearchMovie.setHasFixedSize(true)
        activity?.let {
            val sharedViewModel = ViewModelProviders.of(it).get(SharedViewModel::class.java)

            sharedViewModel.inputNumber.observe(this, Observer {
                it?.let {
                    if(!it.trim().isNotEmpty()){
                        for (i in listSearchMovie.indices) {
                            listSearchMovie.removeAt(0)
                            rvSearchMovie.adapter!!.notifyItemRemoved(0)
                            rvSearchMovie.adapter!!.notifyDataSetChanged()
                        }
                    }
                    else{
                        myViewModel.searchMovie(it,pageMovie)
                        myViewModel.searchMovie.observe(viewLifecycleOwner, Observer {
                            //remove all elements from MutableList when editText changed
                            //we have two ways to remove
                            //case 1:
//                        for (i in listSearchMovie.indices) {
//                            listSearchMovie.removeAt(0)
//                        }
                            //case 2:
                            listSearchMovie.clear()
                            // then add all elements by the new String in editText after changed
                            listSearchMovie.addAll(it.results)
//                            rvSearchMovie.adapter!!.notifyDataSetChanged()
                        })
                        searchMovieAdapter = SearchMovieAdapter(activity as Activity,listSearchMovie,context as Context)
                        rvSearchMovie.adapter = searchMovieAdapter
                    }
                }
            })
        }

        return SearchMovieView
    }
}
