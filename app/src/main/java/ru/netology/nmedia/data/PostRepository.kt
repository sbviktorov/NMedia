package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.socialNetwork.Post

interface PostRepository {

    fun getAll(): LiveData<List<Post>>

    fun likeById(postId: Long)

    fun shareById(postId: Long)

    fun deleteById(postId: Long)

    fun save(post: Post)

    fun cancelUpdate()

    companion object {
        const val newPostID: Long = -1
    }
}