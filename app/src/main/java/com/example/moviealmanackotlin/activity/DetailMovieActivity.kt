package com.example.moviealmanackotlin.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.models.Constant
import com.example.moviealmanackotlin.models.DetailMovieResponse
import com.example.moviealmanackotlin.networkUtils.NetworkConfig
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.content_detail.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieActivity : AppCompatActivity() {

    private val TAG:String ="DetailMovieActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setupView()
        setupToDetailTrailer()

        /*findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

    }

    private fun setupToDetailTrailer() {
        fab_play.setOnClickListener {
           startActivity<TrailerActivity>()
        }
    }

    override fun onStart() {
        super.onStart()
        getMovieDetail()
    }

    private fun setupView(){
        setSupportActionBar(findViewById(R.id.toolbar))
        //findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    private fun showMovie(detail: DetailMovieResponse?) {
        Log.d(TAG, "responseDetail: ${detail?.overview}")
        val backDropPath = Constant.TMDb_BACKDROP_PATH + detail?.backdrop_path
        Picasso.get()
                .load(backDropPath)
                .placeholder(R.drawable.placeholder_portrait)
                .error(R.drawable.placeholder_portrait)
                .fit().centerCrop()
                .into(img_back_drop)

        txt_title_movie_detail.text = detail?.title
        txt_vote_average_detail.text = detail?.vote_average.toString()
        txt_content_overview.text = detail?.overview

        for (genre in detail?.genres!!) {
            txt_genre_detail.text = "${genre.name}"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}