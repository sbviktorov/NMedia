package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.socialNetwork.Post

interface PostRepository {
    val data: LiveData<Post>

    fun like()

    fun share()
}