package com.ranzan.kutukidemo.view.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ranzan.kutukidemo.R
import com.ranzan.kutukidemo.model.CategoryClass
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapter(private val list: ArrayList<CategoryClass>, private val onItemClicked: OnItemClicked) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list[position], onItemClicked)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun setData(categoryClass: CategoryClass, onItemClicked: OnItemClicked) {
            view.apply {
                Glide.with(img).load(categoryClass.image).into(img)
                img.setOnClickListener {
                    onItemClicked.onItemClicked(categoryClass)
                }
            }
        }
    }
}