package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.socialNetwork.Post

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionListener {
    private val repository: PostRepository = FilePostRepository(application)
    val data = repository.getAll()
    private val ownerName = "Нетология. Университет интернет-профессий"
    val sharePostContent = SingleLiveEvent<String>()
    val youtubeURL = SingleLiveEvent<String>()

    //    val navigateToPostContentScreenEvent = SingleLiveEvent<String?>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val navigateToPostScreenEvent = SingleLiveEvent<Long>()
    private val currentPost = MutableLiveData<Post?>(null)

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
    }

    override fun onCancelEditButtonClicked() {
        repository.cancelUpdate()
        currentPost.value = null
    }

    override fun onPlayButtonClicked(post: Post) {
        youtubeURL.value = post.video!!
    }

    override fun onPostAreaClicked(post: Post) {
//        currentPost.value = post
        navigateToPostScreenEvent.value = post.id
    }
//    endregion PostInteractionListener
}