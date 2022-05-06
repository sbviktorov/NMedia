package ru.netology.nmedia.socialNetwork

import ru.netology.nmedia.socialNetwork.objects.Likes
import java.util.*

data class Post(
    val id: Int,
    val ownerName: String,
    val date: Long = 1590086160000,
    val text: String,
    val likes: Likes = Likes(),
    var reposts: Int = 5,
    val views: Int = 5
) {
}