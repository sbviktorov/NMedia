package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.socialNetwork.Post

interface PostRepository {

    fun getAll(): LiveData<List<Post>>

    fun likeById(id: Int)

    fun shareById(id: Int)
}