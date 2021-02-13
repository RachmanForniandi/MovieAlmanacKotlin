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
import kotlinx.android.synthetic.main.fragment_now_playing.view.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NowPlayingFragment : Fragment() {
    private val TAG: String ="NowPlayingFragment"
    private lateinit var v2: View
    lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v2 =inflater.inflate(R.layout.fragment_now_playing, container, false)
        return v2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListNowPlaying()
    }


    override fun onStart() {
        super.onStart()
        getDataMovieNowPlaying()
    }



    private fun setupListNowPlaying() {
        mainAdapter = MainAdapter(arrayListOf(),object : MainAdapter.OnClickListener{
            override fun onClick(movieModel: MovieModel) {
                //movieModel.title?.let { showMessage(it) }
                Constant.MOVIE_ID = movieModel.id
                Constant.MOVIE_TITLE = movieModel.title
                startActivity<DetailMovieActivity>()
            }

        })
        v2.list_now_playing_movie.apply {
            //layoutManager = GridLayoutManager(context,2)
            adapter = mainAdapter
        }
    }

    private fun getDataMovieNowPlaying() {
        showLoading(true)
        NetworkConfig().endPointService.getMoviesNowPlaying(Constant.API_KEY,1).enqueue(object :
            Callback<MovieResponse> {
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
            true -> v2.pg_now_playing.visibility =View.VISIBLE
            false-> v2.pg_now_playing.visibility =View.GONE
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