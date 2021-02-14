package com.example.moviealmanackotlin.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.models.Constant
import com.example.moviealmanackotlin.models.MoviePopularModel
import com.example.moviealmanackotlin.supports.getStringDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_main.view.*

class PopularAdapter  (var movies: ArrayList<MoviePopularModel>, var clickListener: OnClickListener): RecyclerView.Adapter<PopularAdapter.PopularHolder>() {

    private val TAG: String ="MainActivity"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent,false)
        return PopularHolder(view)
    }

    override fun onBindViewHolder(holder: PopularHolder, position: Int) {
        val item = movies[position]

        val formatDateRelease= item.first_air_date.let { it?.let { it1 -> getStringDate(it1) } }

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

        holder.itemView.setOnClickListener {
            clickListener.onClick(item)
        }

    }

    inner class PopularHolder (view: View):RecyclerView.ViewHolder(view){
        val titleMovie = view.txt_title_movie
        val languageMovie = view.txt_language_movie
        val releaseDateMovie = view.txt_release_date_movie
        val imgPosterMovie = view.img_Poster


    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setData(popularMovies:List<MoviePopularModel>){
        movies.clear()
        movies.addAll(popularMovies)
        notifyDataSetChanged()
    }

    fun setDataNext(popularMovies:List<MoviePopularModel>){
        movies.addAll(popularMovies)
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(movieModel: MoviePopularModel)
    }
}