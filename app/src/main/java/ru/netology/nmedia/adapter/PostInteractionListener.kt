package ru.netology.nmedia.adapter

import ru.netology.nmedia.socialNetwork.Post

interface PostInteractionListener {
    fun onButtonOfLikesClicked(post: Post)
    fun onButtonOfSharesClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditClicked(post: Post)
    fun onCancelEditButtonClicked()
    fun onPlayButtonClicked(post: Post)
    fun onPostAreaClicked(post: Post)
}