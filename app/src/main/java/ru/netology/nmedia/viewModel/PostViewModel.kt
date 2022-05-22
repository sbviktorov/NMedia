package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.socialNetwork.Post

class PostViewModel : ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository()
    val data = repository.getAll()

    fun onButtonOfLikeClicked(post: Post) = repository.likeById(post.id)
    fun onButtonOfSharesClicked(post: Post) = repository.shareById(post.id)
}