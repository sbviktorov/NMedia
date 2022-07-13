package ru.netology.nmedia.socialNetwork

import ru.netology.nmedia.socialNetwork.objects.Likes
import java.util.*

data class Post(
    val id: Int,
    val ownerName: String,
    val date: Long = Date().time,
    val text: String,
    val likes: Likes = Likes(),
    var reposts: Int = 0,
    val views: Int = 0,
    val video: String? = null
) {
}