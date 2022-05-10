package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.socialNetwork.Post

interface PostRepository {
    //val data: LiveData<Post>


    fun getAll(): LiveData<List<Post>>

    fun likeById(id: Int)

    //fun like()

    //fun share()

    fun shareById(id: Int)
}