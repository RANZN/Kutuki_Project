package com.ranzan.kutukidemo.view

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranzan.kutukidemo.R
import com.ranzan.kutukidemo.model.VideoClass
import com.ranzan.kutukidemo.view.ItemClickListners.RecommendedItemClicked
import com.ranzan.kutukidemo.view.adpter.RecommendedAdapter
import com.ranzan.kutukidemo.viewmodel.TheViewModel
import com.ranzan.kutukidemo.viewmodel.TheViewModelFactory
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity(), RecommendedItemClicked {

    private lateinit var viewModel: TheViewModel
    private lateinit var dataList: ArrayList<VideoClass>
    private val recommendedList = mutableListOf<VideoClass>()
    private var recommendedListPlaying = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        viewModel = ViewModelProvider(this, TheViewModelFactory()).get(TheViewModel::class.java)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        backBtn.setOnClickListener {
            onBackPressed()
        }

        var itemId = 0

        if (intent != null && intent.hasExtra("id")) {
            itemId = intent.getIntExtra("id", 0)
        }
        viewModel.videoList().observe(this, Observer {
            dataList = it
            setRecyclerView(it[itemId])
            playVideo(it[itemId].videoUrl!!)
        })
    }

    private fun setRecyclerView(data: VideoClass) {
        data.category.forEach { id ->
            dataList.forEach { data ->
                if (data.id == id) recommendedList.add(data)
            }
        }
        recommendedRecyclerView.apply {
            adapter = RecommendedAdapter(recommendedList as ArrayList<VideoClass>, this@VideoActivity)
            layoutManager = LinearLayoutManager(this@VideoActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun playVideo(url: String) {
        val uri = Uri.parse(url)
        videoView.setVideoURI(uri)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(this.videoView)
        mediaController.setMediaPlayer(this.videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
        videoView.setOnCompletionListener {
            Toast.makeText(this, "Playing Next", Toast.LENGTH_SHORT).show()
            if (recommendedListPlaying < recommendedList.size)
                playVideo(recommendedList[recommendedListPlaying++].videoUrl!!)
            else Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemClicked(videoClass: VideoClass) {
        playVideo(videoClass.videoUrl!!)
    }
}