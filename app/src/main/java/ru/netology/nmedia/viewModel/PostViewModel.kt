package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.socialNetwork.Post

class PostViewModel : ViewModel(), PostInteractionListener {
    private val repository: PostRepository = InMemoryPostRepository()
    val data = repository.getAll()
    private val ownerName = "Нетология. Университет интернет-профессий"

    val currentPost = MutableLiveData<Post?>(null)


    fun onSaveButtonClicked(content: String): Boolean {
        if (content.isBlank()) {
            return false
        }
        val post = currentPost.value?.copy(
            text = content
        )
            ?: Post(

                id = PostRepository.newPostID,
                ownerName = ownerName,
                text = content
            )
        repository.save(post)
        currentPost.value = null
        return true
    }

    // region PostInteractionListener
    override fun onButtonOfLikesClicked(post: Post) = repository.likeById(post.id)
    override fun onButtonOfSharesClicked(post: Post) = repository.shareById(post.id)
    override fun onRemoveClicked(post: Post) = repository.deleteById(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post

    }

//    endregion PostInteractionListener
}