package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository()
    //val data by repository::data
    //val data by repository::getAll()
    val data = repository.getAll()

//    fun onButtonOfLikeClicked() = repository.like()
fun onButtonOfLikeClicked(id: Int) = repository.likeById(id)

//    fun onButtonOfSharesClicked() = repository.share()
fun onButtonOfSharesClicked(id: Int) = repository.shareById(id)
}