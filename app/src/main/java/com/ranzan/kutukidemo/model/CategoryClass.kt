package com.ranzan.kutukidemo.model

import java.io.Serializable


data class CategoryClass(
    val id: Int,
    val name: String? = null,
    val image: String? = null
) : Serializable

data class VideoClass(
    val id: Int,
    val title: String? = null,
    val description: String? = null,
    val thumbnail: String? = null,
    val videoUrl: String? = null,
    val category: List<Int>
) : Serializable
