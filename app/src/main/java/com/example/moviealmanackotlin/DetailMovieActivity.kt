package com.example.moviealmanackotlin

import android.os.Bundle
import android.util.Log
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.moviealmanackotlin.models.Constant
import com.example.moviealmanackotlin.models.DetailMovieResponse
import com.example.moviealmanackotlin.networkUtils.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieActivity : AppCompatActivity() {

    private val TAG:String ="DetailMovieActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()
        getMovieDetail()
    }

    private fun getMovieDetail(){
        NetworkConfig().endPointService.getMoviesDetail(Constant.MOVIE_ID,Constant.API_KEY)
                .enqueue(object :Callback<DetailMovieResponse>{
                    override fun onResponse(call: Call<DetailMovieResponse>, response: Response<DetailMovieResponse>) {
                        if (response.isSuccessful){
                            showMovie(response.body())
                        }
                    }

                    override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                        Log.d(TAG,t.toString())
                    }

                })
    }

    private fun showMovie(detailMovie: DetailMovieResponse?) {
        Log.d(TAG,"responseDetail: ${detailMovie?.overview}")
    }
}