package com.example.moviealmanackotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.moviealmanackotlin.adapters.MainAdapter
import com.example.moviealmanackotlin.models.Constant
import com.example.moviealmanackotlin.models.MovieModel
import com.example.moviealmanackotlin.models.MovieResponse
import com.example.moviealmanackotlin.networkUtils.NetworkConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val moviePopular =0
const val movieNowPlaying =1
class MainActivity : AppCompatActivity() {

    private val TAG: String ="MainActivity"

    lateinit var mainAdapter: MainAdapter
    private var movieCategory =0
    private val api = NetworkConfig().endPointService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)
        showAdapterMovie()
        getDataMovie()
    }

    /*override fun onStart() {
        super.onStart()
        getDataMovie()
    }*/

    private fun showAdapterMovie() {
        mainAdapter = MainAdapter(arrayListOf(),object : MainAdapter.OnClickListener{
            override fun onClick(movieModel: MovieModel) {
                //movieModel.title?.let { showMessage(it) }
                Constant.MOVIE_ID = movieModel.id
                Constant.MOVIE_TITLE = movieModel.title
                startActivity<DetailMovieActivity>()
            }

        })
        list_main_movie.apply {
            //layoutManager = GridLayoutManager(context,2)
            adapter = mainAdapter
        }

    }

    private fun getDataMovie() {
        showLoading(true)
        var apiCall:Call<MovieResponse>? = null
        when(movieCategory){
            moviePopular ->{
                apiCall = api.getMoviesPopular(Constant.API_KEY,1)
            }
            movieNowPlaying ->{
                apiCall = api.getMoviesNowPlaying(Constant.API_KEY,1)
            }
        }
        apiCall?.enqueue(object :Callback<MovieResponse>{
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
            true -> pg1.visibility =View.VISIBLE
            false-> pg1.visibility =View.GONE
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_popular ->{
                showMessage("Popular Movie Selected")
                movieCategory = moviePopular
                getDataMovie()
                true
            }

            R.id.action_now_playing ->{
                showMessage("Now Playing Movie Selected")
                movieCategory = movieNowPlaying
                getDataMovie()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
    }
}