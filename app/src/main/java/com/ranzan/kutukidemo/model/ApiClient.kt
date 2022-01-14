package com.ranzan.kutukidemo.model

import com.ranzan.kutukidemo.ResponseImageModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiClient {
    @GET("v2/5e2bebd23100007a00267e51")
    fun getCategory(): Observable<ResponseImageModel>

    @GET("v2/5e2beb5a3100006600267e4e")
    fun getVideos(): Observable<ResponseVideoModel>

}