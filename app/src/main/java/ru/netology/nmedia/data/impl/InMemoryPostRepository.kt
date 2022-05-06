package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.R
import ru.netology.nmedia.socialNetwork.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.socialNetwork.objects.Likes

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
        Post(
            id = 0,
            ownerName = "Нетология. Университет интернет-профессий",
            text = "Привет, это новая Нетология!\n\nКогда-то Нетология начиналась с интенсивов по " +
                    "онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и " +
                    "управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных " +
                    "профессионалов.\n\nНо самое важное остаётся с нами: мы верим, что в каждом уже " +
                    "есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. " +
                    "Наша миссия — помочь встать на путь роста и начать цепочку перемен → " +
                    "http://netolo.gy/fyb"
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value)
        val likedPost = currentPost.copy(
            likes = Likes(
                userLikes = !currentPost.likes.userLikes,
                count = if (currentPost.likes.userLikes) {
                    currentPost.likes.count - 1
                } else {
                    currentPost.likes.count + 1
                }
            )
        )

        data.value = likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value)
        val sharedPost = currentPost.copy(reposts = currentPost.reposts + 1)
        data.value = sharedPost
    }


}