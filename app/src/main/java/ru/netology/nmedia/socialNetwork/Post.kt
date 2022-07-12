package ru.netology.nmedia.socialNetwork

import kotlinx.serialization.Serializable
import ru.netology.nmedia.socialNetwork.objects.Likes
import java.util.*

@Serializable
data class Post(
    val id: Long,
    val ownerName: String,
    val date: Long = Date().time,
    val text: String,
    var likesCount: Int = 0,
    var userLikes: Boolean = false,
//    val likes: Likes = Likes(),
    var reposts: Int = 0,
    val views: Int = 0,
    val video: String? = null
) {
}