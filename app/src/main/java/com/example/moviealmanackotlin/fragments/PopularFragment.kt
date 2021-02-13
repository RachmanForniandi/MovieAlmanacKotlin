package com.example.moviealmanackotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.activity.DetailMovieActivity
import com.example.moviealmanackotlin.adapters.MainAdapter
import com.example.moviealmanackotlin.models.Constant
import com.example.moviealmanackotlin.models.MovieModel
import com.example.moviealmanackotlin.models.MovieResponse
import com.example.moviealmanackotlin.networkUtils.NetworkConfig
import kotlinx.android.synthetic.main.fragment_popular.view.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularFragment : Fragment() {
    private val TAG: String ="PopularFragment"
    private lateinit var v1: View
    lateinit var mainAdapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v1 =inflater.inflate(R.layout.fragment_popular, container, false)
        return v1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListPopular()
    }

    override fun onStart() {
        super.onStart()
        getDataMoviePopular()
    }

    private fun setupListPopular() {
        mainAdapter = MainAdapter(arrayListOf(),object : MainAdapter.OnClickListener{
            override fun onClick(movieModel: MovieModel) {
                //movieModel.title?.let { showMessage(it) }
                Constant.MOVIE_ID = movieModel.id
                Constant.MOVIE_TITLE = movieModel.title
                startActivity<DetailMovieActivity>()
            }

        })
        v1.list_popular.apply {
            //layoutManager = GridLayoutManager(context,2)
            adapter = mainAdapter
        }
    }

    private fun getDataMoviePopular() {
        showLoading(true)
        NetworkConfig().endPointService.getMoviesPopular(Constant.API_KEY,1).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                showLoading(false)
                if (response.isSuccessful){
                    showDataMovie(response.body())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG,"errorResponse: $t")
                showLoading(false)
            }
        })
    }

    private fun showLoading(loading: Boolean) {
        when(loading){
            true -> v1.pg_popular.visibility =View.VISIBLE
            false-> v1.pg_popular.visibility =View.GONE
        }
    }


    private fun showDataMovie(response: MovieResponse?) {
        /*Log.d(TAG,"responseMovie: $response")
        Log.d(TAG,"total_pages ${response?.total_pages}")

        for (movie in response?.results!!){
            Log.d(TAG,"movie_title: ${movie.title}")
        }*/
        response?.let { mainAdapter.setData(response.results) }
    }



}