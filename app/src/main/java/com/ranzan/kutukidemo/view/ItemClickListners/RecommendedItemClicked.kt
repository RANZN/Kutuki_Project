package com.ranzan.kutukidemo.view.ItemClickListners

import com.ranzan.kutukidemo.model.VideoClass

interface RecommendedItemClicked {
    fun onItemClicked(videoClass: VideoClass, pos: Int)
}