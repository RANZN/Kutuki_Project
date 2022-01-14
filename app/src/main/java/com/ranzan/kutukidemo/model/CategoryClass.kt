package com.ranzan.kutukidemo.model

import java.io.Serializable


data class CategoryClass(
    val id: Int,
    val name: String? = null,
    val image: String? = null
) : Serializable

