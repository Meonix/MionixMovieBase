package com.nice.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.nice.myapplication.R
import com.nice.myapplication.view.adapter.SearchPagerAdapter
import com.nice.myapplication.view.fragment.SearchMovieFragment
import com.nice.myapplication.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private lateinit var fragmentAdapter: SearchPagerAdapter
    private lateinit var vpSearch : ViewPager
    private lateinit var etSearch: EditText
    private var sharedViewModel: SharedViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        if(findViewById<ViewPager>(R.id.vpSearch) != null){
            if(savedInstanceState != null){
                return
            }
            val fragmentManager : FragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().add(R.id.vpSearch,SearchMovieFragment(),null).commit()
        }
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
//        val intent = Intent(this@SearchActivity, SearchMovieFragment::class.java)
//        intent.putExtra("query","ad")
        supportFragmentManager.beginTransaction().add(R.id.vpSearch, SearchMovieFragment()).commit()
        etSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    var input = ""
                    if (s.toString().isNotEmpty()) {
                        input = s.toString()
                    }
                    sharedViewModel?.inputNumber?.postValue(input)
                }
            }
        })

    }
    private fun initView(){
        vpSearch = findViewById(R.id.vpSearch)
        etSearch = findViewById(R.id.etSearch)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        fragmentAdapter = SearchPagerAdapter(supportFragmentManager)
        vpSearch.adapter = fragmentAdapter

        tlSearch.setupWithViewPager(vpSearch,true)
    }



}
