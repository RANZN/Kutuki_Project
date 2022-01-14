package com.ranzan.kutukidemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.ranzan.kutukidemo.model.CategoryClass
import com.ranzan.kutukidemo.model.Network
import com.ranzan.kutukidemo.model.VideoClass
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject
import java.util.*


object TheViewModel : ViewModel() {

    private val imageList = mutableListOf<CategoryClass>()
    private val videoList = mutableListOf<VideoClass>()

    private val liveImageData = MutableLiveData<MutableList<CategoryClass>>()
    private val liveVideoData = MutableLiveData<MutableList<VideoClass>>()

    fun getImageData() {
        Network.getRetrofit().getCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Any) {
                    val gson = Gson()
                    val jsonObject: JsonObject = gson.toJsonTree(t).getAsJsonObject()
                    val json: String = gson.toJson(jsonObject)
                    val jsonData = JSONObject(json)
                    try {
                        val responseJSON = jsonData.getJSONObject("response")
                        val jsonArray = responseJSON.getJSONObject("videoCategories")
                        val key = jsonArray.keys()
                        while (key.hasNext()) {
                            val value = key.next()
                            val obj = jsonArray.getJSONObject(value)
                            val name = obj.getString("name")
                            val image = obj.getString("image")
                            val id = name.split("Category")[1].toInt()

                            imageList.add(CategoryClass(id, name, image))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }


                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    Collections.sort(imageList, object : Comparator<CategoryClass> {
                        override fun compare(p0: CategoryClass?, p1: CategoryClass?): Int {
                            return p0!!.id.compareTo(p1!!.id)
                        }
                    })
                    liveImageData.postValue(imageList)
                }
            })

    }


    fun getVideoData() {
        Network.getRetrofit().getVideos()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Any) {
                    val gson = Gson()
                    val jsonObject: JsonObject = gson.toJsonTree(t).getAsJsonObject()
                    val json: String = gson.toJson(jsonObject)
                    val jsonData = JSONObject(json)
                    try {
                        val responseJSON = jsonData.getJSONObject("response")
                        val jsonArray = responseJSON.getJSONObject("videos")
                        val key = jsonArray.keys()
                        while (key.hasNext()) {
                            val value = key.next()
                            val obj = jsonArray.getJSONObject(value)
                            val title = obj.getString("title")
                            val id = title.split("Video ")[1].toInt()
                            val description = obj.getString("description")
                            val thumbnailURL = obj.getString("thumbnailURL")
                            val videoURL = obj.getString("videoURL")
                            val category = obj.getString("categories")
                            val cateList = category.split(",")
                            val cateListInt = mutableListOf<Int>()
                            cateList.forEach {
                                cateListInt.add(it.split("Category")[1].toInt())
                            }
                            videoList.add(VideoClass(id, title, description, thumbnailURL, videoURL, cateListInt))

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
//                    Collections.sort(videoList, object : Comparator<VideoClass> {
//                        override fun compare(p0: VideoClass?, p1: VideoClass?): Int {
//                            return p0!!.id.compareTo(p1!!.id)
//                        }
//
//                    })
                    liveVideoData.postValue(videoList)
                }
            })

    }


    fun imageList() = liveImageData as MutableLiveData<ArrayList<CategoryClass>>

    fun videoList() = liveVideoData as MutableLiveData<ArrayList<VideoClass>>

}


class TheViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TheViewModel as T
    }

}

