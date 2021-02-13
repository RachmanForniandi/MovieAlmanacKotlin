package com.example.moviealmanackotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviealmanackotlin.R
import com.example.moviealmanackotlin.models.TrailerModel
import kotlinx.android.synthetic.main.item_trailer.view.*

class TrailerAdapter (var trailers: ArrayList<TrailerModel>, var clickListener: TrailerAdapter.OnAdapterListener): RecyclerView.Adapter<TrailerAdapter.TrailerHolder>() {

    private val TAG: String ="TrailerActivity"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer,parent,false)
        return TrailerHolder(view)
    }

    override fun onBindViewHolder(holder: TrailerHolder, position: Int) {
        val video = trailers[position]
        holder.titleTrailer.text = video.name

        holder.itemView.setOnClickListener {
            video.key?.let { it1 -> clickListener.onClick(it1) }
        }
    }

    inner class TrailerHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val titleTrailer = itemView.txt_title_trailer_video
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    fun setDataTrailer(newVideos:List<TrailerModel>){
        trailers.clear()
        trailers.addAll(newVideos)
        notifyDataSetChanged()
        newVideos[0].key?.let { clickListener.onVideo(it) }
    }

    interface OnAdapterListener {
        fun onClick(key:String)
        fun onVideo(key:String)
    }
}