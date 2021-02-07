package com.example.moviealmanackotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.models.MovieModel
import com.example.moviealmanackotlin.supports.getStringDate
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter (var movies: ArrayList<MovieModel>):
        RecyclerView.Adapter<MainAdapter.MovieHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent,false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item = movies.get(position)

        val formatDateRelease:String = item.release_date?.let {  getStringDate(it)}?: "-"

        holder.titleMovie.text = item.title
        holder.languageMovie.text = item.original_language
        holder.releaseDateMovie.text = formatDateRelease

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


