package com.example.moviealmanackotlin.activity


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.adapters.TrailerAdapter
import com.example.moviealmanackotlin.models.Constant
import com.example.moviealmanackotlin.models.TrailerResponse
import com.example.moviealmanackotlin.networkUtils.NetworkConfig
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_trailer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TrailerActivity : AppCompatActivity() {

    private val TAG:String ="TrailerActivity"

    lateinit var youTubePlayer: YouTubePlayer
    lateinit var trailerAdapter: TrailerAdapter
    private var cueKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trailer)

        setupView()
        setupListener()
        setupTrailerRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        getMovieTrailer()
    }

    private fun setupView() {
        supportActionBar?.title = Constant.MOVIE_TITLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lifecycle.addObserver(yt_player_view)

        yt_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                super.onReady(player)
                youTubePlayer = player
                cueKey.let {
                    youTubePlayer.cueVideo("",0f)
                }
            }
        })
    }
    private fun setupTrailerRecyclerView() {
        trailerAdapter = TrailerAdapter(arrayListOf(), object : TrailerAdapter.OnAdapterListener {
            override fun onClick(key: String) {
                youTubePlayer.let {
                    it.cueVideo(key,0f)
                }
            }
            override fun onVideo(key: String) {
                cueKey = key
            }

        })
        list_video_trailer.apply {
            //layoutManager = GridLayoutManager(context,2)
            adapter = trailerAdapter
        }

    }




    private fun setupListener() {
    }

    private fun getMovieTrailer(){
        showLoading(true)
        NetworkConfig().endPointService.getMoviesTrailer(Constant.MOVIE_ID, Constant.API_KEY)
                .enqueue(object : Callback<TrailerResponse> {
                    override fun onResponse(call: Call<TrailerResponse>, response: Response<TrailerResponse>) {
                        Log.d(TAG, response.toString())
                        showLoading(false)
                        if (response.isSuccessful) {
                            val resultsTrailer: TrailerResponse? = response.body()
                            showTrailer(resultsTrailer)
                        }
                    }

                    override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                        Log.d(TAG, t.toString())
                        showLoading(false)
                    }

                })
    }

    private fun showLoading(loading: Boolean) {
        when(loading){
            true -> {
                pg_trailer.visibility = View.VISIBLE
                list_video_trailer.visibility = View.GONE
            }
            false -> {
                pg_trailer.visibility = View.GONE
                list_video_trailer.visibility = View.VISIBLE
            }
        }
    }

    private fun showTrailer(response: TrailerResponse?) {
        if (response != null) {
            for (res in response.results){
                Log.d(TAG, "nameVideoTrailer: ${res.name}")
                trailerAdapter.setDataTrailer(response.results)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


}