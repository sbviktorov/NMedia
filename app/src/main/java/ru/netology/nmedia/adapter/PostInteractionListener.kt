package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.Post

interface PostInteractionListener {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onAddClicked()
    fun onEditClicked(post: Post)
    fun onCancelEditClicked()
    fun onPlayClicked(post: Post)
    fun onPostClicked(postId: Int)
}