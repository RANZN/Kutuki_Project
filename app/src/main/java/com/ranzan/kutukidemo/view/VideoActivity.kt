package com.ranzan.kutukidemo.view


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.ranzan.kutukidemo.R
import com.ranzan.kutukidemo.model.VideoClass
import com.ranzan.kutukidemo.repository.Repo
import com.ranzan.kutukidemo.view.ItemClickListners.RecommendedItemClicked
import com.ranzan.kutukidemo.view.adpter.RecommendedAdapter
import com.ranzan.kutukidemo.viewmodel.TheViewModel
import com.ranzan.kutukidemo.viewmodel.TheViewModelFactory
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.exo_playback_control_view.*


class VideoActivity : AppCompatActivity(), RecommendedItemClicked, Player.Listener {

    private lateinit var viewModel: TheViewModel
    private lateinit var dataList: ArrayList<VideoClass>
    private val recommendedList = mutableListOf<VideoClass>()
    private var recommendedListPlaying = 1
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playingVideo: VideoClass
    private var fullscreen = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        viewModel = ViewModelProvider(this, TheViewModelFactory(Repo)).get(TheViewModel::class.java)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

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
                    return@forEach
                }
            }
        })

        fullscreenButton.setOnClickListener {
            fullScreen()
        }
    }

    private fun fullScreen() {
        if (fullscreen) {
            fullscreenButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen))
            val params = videoPlayer.layoutParams
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
            params.height = (0 * applicationContext.resources.displayMetrics.density).toInt()
            videoPlayer.layoutParams = params
            backBtn.visibility = View.VISIBLE
            fullscreen = false

        } else {
            backBtn.visibility = View.GONE
            fullscreenButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen_exit))
            val params = videoPlayer.layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            videoPlayer.layoutParams = params

            fullscreen = true
        }
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
        recommendedListPlaying = pos+1
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.release()
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_BUFFERING)
            videoProgressBar.visibility = View.VISIBLE
        else if (playbackState == Player.STATE_READY)
            videoProgressBar.visibility = View.INVISIBLE
        else if (playbackState == Player.STATE_ENDED) {
            if (recommendedListPlaying < recommendedList.size ) {
                Toast.makeText(this, "Playing Next", Toast.LENGTH_SHORT).show()
                setRecyclerView(recommendedList[recommendedListPlaying].id)
                playVideo(recommendedList[recommendedListPlaying++].videoUrl!!)
            } else {
                Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show()
                videoPlayer.keepScreenOn = false
            }
        }
    }
}
