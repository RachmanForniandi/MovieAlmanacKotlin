package com.example.moviealmanackotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.adapters.MainAdapter
import com.example.moviealmanackotlin.adapters.PopularAdapter
import com.example.moviealmanackotlin.models.*
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
    lateinit var popularAdapter: PopularAdapter
    private var movieCategory =0
    private val api = NetworkConfig().endPointService
    private var isScrolling = false
    private var currentPage = 1
    private var totalPages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)
        showAdapterMovie()
        showAdapterMovie2()
        setupListener()
    }




    override fun onStart() {
        super.onStart()
        getDataMovie()
        getDataMovie2()
        showLoadingNext(false)
    }

    private fun setupListener() {
        scrollview_main.setOnScrollChangeListener(object :NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight){
                    if (!isScrolling){
                        if (currentPage <= totalPages){
                            getDataMovieNextPageNowPlaying()
                            getDataMovieNextPagePopular()
                        }
                    }
                }
            }

        })
    }


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
    private fun showAdapterMovie2() {
        popularAdapter = PopularAdapter(arrayListOf(),object : PopularAdapter.OnClickListener{
            override fun onClick(moviePopularModel: MoviePopularModel) {
                //movieModel.title?.let { showMessage(it) }
                Constant.MOVIE_ID = moviePopularModel.id
                Constant.MOVIE_TITLE = moviePopularModel.title
                startActivity<DetailMovieActivity>()
            }

        })

        list_main_movie.apply {
            //layoutManager = GridLayoutManager(context,2)
            adapter = popularAdapter
        }

    }
    private fun getDataMovie() {
        scrollview_main.scrollTo(0,0)
        currentPage =1
        showLoading(true)
        var apiCall:Call<MovieResponse>? = null
        //var apiCall2:Call<PopularResponse>? = null


        apiCall = api.getMoviesNowPlaying(Constant.API_KEY,1)

        apiCall.enqueue(object :Callback<MovieResponse>{
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

        /*apiCall2?.enqueue(object :Callback<PopularResponse>{
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
        })*/
    }
    private fun getDataMovie2() {
        scrollview_main.scrollTo(0,0)
        currentPage = currentPage+1
        showLoading(true)
        var apiCall2:Call<PopularResponse>? = null

        apiCall2 = api.getMoviesPopular(Constant.API_KEY,1)

        apiCall2.enqueue(object :Callback<PopularResponse>{
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
    private fun getDataMovieNextPageNowPlaying() {
        currentPage =currentPage+1
        showLoadingNext(true)
        var apiCall:Call<MovieResponse>? = null
        apiCall = api.getMoviesNowPlaying(Constant.API_KEY,currentPage)

        apiCall.enqueue(object :Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                showLoadingNext(false)
                if (response.isSuccessful){
                    showDataMovieNext(response.body())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG,"errorResponse: $t")
                showLoadingNext(false)
            }
        })

    }

    private fun getDataMovieNextPagePopular() {
        currentPage +=1
        showLoadingNext(true)
        var apiCall2:Call<PopularResponse>? = null
        apiCall2 = api.getMoviesPopular(Constant.API_KEY,currentPage)
        apiCall2.enqueue(object :Callback<PopularResponse>{
            override fun onResponse(call: Call<PopularResponse>, response: Response<PopularResponse>) {
                showLoading(false)
                if (response.isSuccessful){
                    showDataPopularMovieNext(response.body())
                }
            }

            override fun onFailure(call: Call<PopularResponse>, t: Throwable) {
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

    private fun showLoadingNext(loading: Boolean) {
        when(loading){
            true -> {
                isScrolling = true
                pg1_next_page.visibility =View.VISIBLE
            }
            false-> {
                isScrolling = false
                pg1_next_page.visibility =View.GONE
            }
        }
    }


    private fun showDataMovie(response: MovieResponse?) {
        totalPages = response?.total_pages!!.toInt()
        response.let { mainAdapter.setData(response.results) }
    }
    private fun showDataMovieNext(response: MovieResponse?) {
        totalPages = response?.total_pages!!.toInt()
        response.let { mainAdapter.setDataNext(response.results) }
        showMessage("Page $currentPage")
    }

    private fun showDataPopularMovie(response: PopularResponse?) {
        totalPages = response?.total_pages!!.toInt()
        response.let { popularAdapter.setData(response.results) }
    }
    private fun showDataPopularMovieNext(response: PopularResponse?) {
        totalPages = response?.total_pages!!.toInt()
        response.let { popularAdapter.setDataNext(response.results) }
        showMessage("Page $currentPage")
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