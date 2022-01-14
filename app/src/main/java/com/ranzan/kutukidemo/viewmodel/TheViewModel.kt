package com.ranzan.kutukidemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ranzan.kutukidemo.repository.Repo


class TheViewModel(private val repo: Repo) : ViewModel() {


    fun fetchData() {
        repo.fetchData()
    }

    fun getImageList() = repo.imageList()

    fun videoList() = repo.videoList()

}

class TheViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TheViewModel(repo) as T
    }

}

