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

    val sharePostContent = SingleLiveEvent<String>()
    val youtubeURL = SingleLiveEvent<String>()
//    val navigateToPostContentScreenEvent = SingleLiveEvent<Unit>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String?>()

    val currentPost = MutableLiveData<Post?>(null)
    val editPost = SingleLiveEvent<String>()

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

    fun onAddClicked() {
//        navigateToPostContentScreenEvent.call()
        navigateToPostContentScreenEvent.value = null
    }

    // region PostInteractionListener
    override fun onButtonOfLikesClicked(post: Post) = repository.likeById(post.id)
    override fun onButtonOfSharesClicked(post: Post) {
        sharePostContent.value = post.text
        repository.shareById(post.id)
    }

    override fun onRemoveClicked(post: Post) = repository.deleteById(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.text
//        navigateToPostContentScreenEvent.call()
    }

    override fun onCancelEditButtonClicked() {
        repository.cancelUpdate()
        currentPost.value = null
    }

    override fun onPlayButtonClicked(post: Post) {
        youtubeURL.value = post.video!!
    }


//    endregion PostInteractionListener
}