package com.ranzan.kutukidemo.view


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.ranzan.kutukidemo.R
import com.ranzan.kutukidemo.model.VideoClass
import com.ranzan.kutukidemo.view.ItemClickListners.RecommendedItemClicked
import com.ranzan.kutukidemo.view.adpter.RecommendedAdapter
import com.ranzan.kutukidemo.viewmodel.TheViewModel
import com.ranzan.kutukidemo.viewmodel.TheViewModelFactory
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity(), RecommendedItemClicked, Player.Listener {

    private lateinit var viewModel: TheViewModel
    private lateinit var dataList: ArrayList<VideoClass>
    private val recommendedList = mutableListOf<VideoClass>()
    private var recommendedListPlaying = 0
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playingVideo: VideoClass
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

            dataList.forEach { data ->
                if (data.id == itemId) {
                    playingVideo = data
                    playVideo(data.videoUrl!!)
                    setRecommended()
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        exoPlayer = ExoPlayer.Builder(this).build()
    }

    private fun setRecommended() {
        recommendedList.add(playingVideo)
        playingVideo.category.forEach { id ->
            dataList.forEach { data ->
                if (data.id == id && !recommendedList.contains(data)) recommendedList.add(data)
            }
        }
        setRecyclerView(playingVideo.id)
    }

    private fun setRecyclerView(id: Int, pos: Int = 0) {
        recommendedRecyclerView.apply {
            adapter = RecommendedAdapter(recommendedList as ArrayList<VideoClass>, this@VideoActivity, id)
            layoutManager = LinearLayoutManager(this@VideoActivity, LinearLayoutManager.HORIZONTAL, false)
            smoothScrollToPosition(pos)
        }
    }

    private fun playVideo(url: String) {
        val uri = Uri.parse(url)
        videoPlayer.apply {
            player = exoPlayer
            keepScreenOn = true

        }
        exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            play()
            addListener(this@VideoActivity)
        }
    }

    override fun onItemClicked(videoClass: VideoClass, pos: Int) {
        playVideo(videoClass.videoUrl!!)
        setRecyclerView(videoClass.id, pos)
        recommendedListPlaying = pos
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.release()
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        if (playbackState == Player.STATE_BUFFERING)
            videoProgressBar.visibility = View.VISIBLE
        else if (playbackState == Player.STATE_READY)
            videoProgressBar.visibility = View.INVISIBLE
        else if (playbackState == Player.STATE_ENDED) {
            if (recommendedListPlaying < recommendedList.size - 1) {
                Toast.makeText(this, "Playing Next", Toast.LENGTH_SHORT).show()
                playVideo(recommendedList[recommendedListPlaying++].videoUrl!!)
                setRecyclerView(recommendedList[recommendedListPlaying].id)
            } else Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show()
        }
    }
}
