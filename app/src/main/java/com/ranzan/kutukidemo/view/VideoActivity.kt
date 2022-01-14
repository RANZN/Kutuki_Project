package com.ranzan.kutukidemo.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ranzan.kutukidemo.R
import com.ranzan.kutukidemo.model.VideoClass
import com.ranzan.kutukidemo.viewmodel.TheViewModel
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() {

    private lateinit var viewModel: TheViewModel
    private lateinit var list: ArrayList<VideoClass>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        viewModel = ViewModelProvider(this).get(TheViewModel::class.java)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()


        list = ArrayList<VideoClass>()

        viewModel.videoList().observe(this, Observer {
//            list = it
            Log.d("dataList", it.toString())
        })
        backBtn.setOnClickListener {
            onBackPressed()
        }
        if (intent != null && intent.hasExtra("id")) {
            val pos = intent.getIntExtra("id", 0)
            Log.d("data", list.toString())
//            playVideo(list[pos].videoUrl!!)
//            setRecyclerView(list[pos])
        }
    }

    private fun setRecyclerView(data: VideoClass) {
        val intList = mutableListOf<Int>()
        data.category.forEach {

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
    }

}