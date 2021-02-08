package com.example.moviealmanackotlin.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.models.Constant
import com.example.moviealmanackotlin.models.MovieModel
import com.example.moviealmanackotlin.supports.getStringDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter (var movies: ArrayList<MovieModel>):
        RecyclerView.Adapter<MainAdapter.MovieHolder>(){

    private val TAG: String ="MainActivity"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent,false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item = movies[position]

        val formatDateRelease:String = item.release_date?.let {  getStringDate(it)}?: "-"

        holder.titleMovie.text = item.title
        holder.languageMovie.text = item.original_language
        holder.releaseDateMovie.text = formatDateRelease
        val posterPath = Constant.TMDb_POSTER_PATH + item.poster_path
        val dropPath = Constant.TMDb_BACKDROP_PATH + item.backdrop_path
        Log.d(TAG,"test_backDropPath: ${dropPath}")
        Picasso.get()
                .load(posterPath)
                .placeholder(R.drawable.placeholder_portrait)
                .error(R.drawable.placeholder_portrait)
                .into(holder.imgPosterMovie)

    }

    inner class MovieHolder (view: View):RecyclerView.ViewHolder(view){
        val titleMovie = view.txt_title_movie
        val languageMovie = view.txt_language_movie
        val releaseDateMovie = view.txt_release_date_movie
        val imgPosterMovie = view.img_Poster

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setData(newMovies:List<MovieModel>){
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    /*interface OnClickListener {

    }*/
}


