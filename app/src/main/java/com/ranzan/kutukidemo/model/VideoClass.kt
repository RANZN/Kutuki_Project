package com.ranzan.kutukidemo.model

data class VideoClass(
    val id: Int,
    val title: String? = null,
    val description: String? = null,
    val thumbnail: String? = null,
    val videoUrl: String? = null,
    val category: List<Int>
)
