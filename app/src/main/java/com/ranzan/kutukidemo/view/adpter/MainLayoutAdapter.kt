package com.ranzan.kutukidemo.view.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ranzan.kutukidemo.R
import com.ranzan.kutukidemo.model.CategoryClass
import com.ranzan.kutukidemo.view.ItemClickListners.ItemCategoryClicked
import kotlinx.android.synthetic.main.item_layout.view.*

class MainLayoutAdapter(private val list: ArrayList<CategoryClass>, private val itemCategoryClicked: ItemCategoryClicked) :
    RecyclerView.Adapter<MainLayoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list[position], itemCategoryClicked)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun setData(categoryClass: CategoryClass, itemCategoryClicked: ItemCategoryClicked) {
            view.apply {
                Glide.with(img).load(categoryClass.image).apply(RequestOptions.circleCropTransform()).into(img)
                img.setOnClickListener {
                    itemCategoryClicked.onItemClicked(categoryClass)
                }
            }
        }
    }
}