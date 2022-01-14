package com.ranzan.kutukidemo.view.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ranzan.kutukidemo.R
import com.ranzan.kutukidemo.model.VideoClass
import com.ranzan.kutukidemo.view.ItemClickListners.RecommendedItemClicked
import kotlinx.android.synthetic.main.recommended_item_layout.view.*

class RecommendedAdapter(private val list: ArrayList<VideoClass>, private val recommendedItemClicked: RecommendedItemClicked, private val ID: Int) :
    RecyclerView.Adapter<RecommendedAdapter.RecommendedVideHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (list[position].id == ID) return 2
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedVideHolder {
        if (viewType == 1) {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommended_item_layout, parent, false)
        return RecommendedVideHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recommended_playing_layout, parent, false)
            return RecommendedVideHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecommendedVideHolder, position: Int) {
        holder.setData(list[position], recommendedItemClicked)
    }

    override fun getItemCount(): Int = list.size

    class RecommendedVideHolder(val view: View) : RecyclerView.ViewHolder(view) {


        fun setData(videoClass: VideoClass, recommendedItemClicked: RecommendedItemClicked) {
            Glide.with(view.recommendedImage).load(videoClass.thumbnail).placeholder(R.drawable.ic_broken).into(view.recommendedImage)
            view.recommendedImage.setOnClickListener {
                recommendedItemClicked.onItemClicked(videoClass, adapterPosition)
            }
        }

        fun highlight() {
            if (true) view.recommendedImage.foreground = getDrawable(view.context, R.drawable.img_border)
            else view.recommendedImage.foreground = getDrawable(view.context, R.drawable.img_no_border)
        }
    }
}