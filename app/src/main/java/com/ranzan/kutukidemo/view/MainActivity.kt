package com.ranzan.kutukidemo.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ranzan.kutukidemo.R
import com.ranzan.kutukidemo.model.CategoryClass
import com.ranzan.kutukidemo.view.adpter.MainLayoutAdapter
import com.ranzan.kutukidemo.view.ItemClickListners.OnItemClicked
import com.ranzan.kutukidemo.viewmodel.TheViewModel
import com.ranzan.kutukidemo.viewmodel.TheViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), OnItemClicked {

    private lateinit var viewModel: TheViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        viewModel = ViewModelProvider(this, TheViewModelFactory()).get(TheViewModel::class.java)

        viewModel.getImageData()
        viewModel.getVideoData()


        viewModel.imageList().observe(this, Observer {
            setRecyclerView(it)
        })
    }

    private fun setRecyclerView(list: List<CategoryClass>) {
        recyclerView.apply {
            adapter = MainLayoutAdapter(list as ArrayList<CategoryClass>, this@MainActivity)
            layoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.HORIZONTAL, false)
        }
        progressBar.visibility = View.GONE
    }

    override fun onItemClicked(categoryClass: CategoryClass) {
        try {
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("id", categoryClass.id)
            startActivity(intent)
        }catch (e:Exception){
            Toast.makeText(this, "Cannot open", Toast.LENGTH_SHORT).show()
        }
    }
}