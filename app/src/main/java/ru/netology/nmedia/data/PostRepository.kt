package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.socialNetwork.Post

interface PostRepository {

    fun getAll(): LiveData<List<Post>>

    fun likeById(postId: Int)

    fun shareById(postId: Int)

    fun deleteById(postId: Int)

    fun save(post: Post)

    companion object {
        const val newPostID: Int = -1
    }
}