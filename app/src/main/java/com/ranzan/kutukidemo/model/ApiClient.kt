package com.ranzan.kutukidemo.model

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiClient {
    @GET("v2/5e2bebd23100007a00267e51")
    fun getCategory(): Observable<Any>

    @GET("v2/5e2beb5a3100006600267e4e")
    fun getVideos(): Observable<Any>

}