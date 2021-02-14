package com.example.moviealmanackotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.activity.DetailMovieActivity
import com.example.moviealmanackotlin.adapters.MainAdapter
import com.example.moviealmanackotlin.adapters.PopularAdapter
import com.example.moviealmanackotlin.models.*
import com.example.moviealmanackotlin.networkUtils.NetworkConfig
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_popular.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularFragment : Fragment() {
    private val TAG: String ="PopularFragment"
    private lateinit var v1: View
    lateinit var popularAdapter: PopularAdapter
    private var isScrolling = false
    private var currentPage = 1
    private var totalPages = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v1 =inflater.inflate(R.layout.fragment_popular, container, false)
        return v1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListPopular()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        getDataMoviePopular()
        showLoadingNextPopular(false)
    }

    private fun setupListener() {
        v1.scrollview_main.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight){
                    if (!isScrolling){
                        if (currentPage <= totalPages){
                            getDataMovieNextPagePopular()
                        }
                    }
                }
            }

        })
    }



    private fun setupListPopular() {
        popularAdapter = PopularAdapter(arrayListOf(),object : PopularAdapter.OnClickListener{
            override fun onClick(movieModel: MoviePopularModel) {
                //movieModel.title?.let { showMessage(it) }
                Constant.MOVIE_ID = movieModel.id
                Constant.MOVIE_TITLE = movieModel.title
                startActivity<DetailMovieActivity>()
            }

        })
        v1.list_popular.apply {
            //layoutManager = GridLayoutManager(context,2)
            adapter = popularAdapter
        }
    }

    private fun getDataMoviePopular() {
        currentPage =1
        showLoading(true)
        NetworkConfig().endPointService.getMoviesPopular(Constant.API_KEY,1).enqueue(object : Callback<PopularResponse> {
            override fun onResponse(call: Call<PopularResponse>, response: Response<PopularResponse>) {
                showLoading(false)
                if (response.isSuccessful){
                    showDataPopularMovie(response.body())
                }
            }

            override fun onFailure(call: Call<PopularResponse>, t: Throwable) {
                Log.d(TAG,"errorResponse: $t")
                showLoading(false)
            }
        })
    }

    private fun getDataMovieNextPagePopular() {
        currentPage =currentPage+1
        showLoadingNextPopular(true)
        NetworkConfig().endPointService.getMoviesPopular(Constant.API_KEY,currentPage).enqueue(object : Callback<PopularResponse> {
            override fun onResponse(call: Call<PopularResponse>, response: Response<PopularResponse>) {
                showLoadingNextPopular(false)
                if (response.isSuccessful){
                    showDataPopularMovieNext(response.body())
                }
            }

            override fun onFailure(call: Call<PopularResponse>, t: Throwable) {
                Log.d(TAG,"errorResponse: $t")
                showLoadingNextPopular(false)
            }
        })
    }
    private fun showLoading(loading: Boolean) {
        when(loading){
            true -> v1.pg_popular.visibility =View.VISIBLE
            false-> v1.pg_popular.visibility =View.GONE
        }
    }

    private fun showLoadingNextPopular(loading: Boolean) {
        when(loading){
            true -> {
                isScrolling = true
                v1.pg_popular_next_page.visibility =View.VISIBLE
            }
            false-> {
                isScrolling = false
                v1.pg_popular_next_page.visibility =View.GONE
            }
        }
    }


    private fun showDataPopularMovie(response: PopularResponse?) {
        totalPages = response?.total_pages!!.toInt()
        response.let { popularAdapter.setData(response.results) }
    }
    private fun showDataPopularMovieNext(response: PopularResponse?) {
        totalPages = response?.total_pages!!.toInt()
        response.let { popularAdapter.setDataNext(response.results) }
        toast("Page $currentPage")
    }



}