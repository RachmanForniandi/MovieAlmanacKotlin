package com.example.moviealmanackotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.moviealmanackotlin.models.Constant
import com.example.moviealmanackotlin.models.MovieResponse
import com.example.moviealmanackotlin.networkUtils.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG: String ="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        getDataMovie()
    }

    private fun getDataMovie() {
        NetworkConfig().endPointService.getMoviesNowPlaying(Constant.API_KEY,1)
                .enqueue(object :Callback<MovieResponse>{
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                        if (response.isSuccessful){
                            checkDebugData(response.body())
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d(TAG,"errorResponse: $t")
                    }
                })
    }

    private fun checkDebugData(response: MovieResponse?) {
        Log.d(TAG,"responseMovie: $response")
        Log.d(TAG,"total_pages ${response?.total_pages}")

        for (movie in response?.results!!){
            Log.d(TAG,"movie_title: ${movie.title}")
        }
    }
}