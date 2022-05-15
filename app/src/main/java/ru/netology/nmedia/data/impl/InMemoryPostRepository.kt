package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.socialNetwork.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.socialNetwork.objects.Likes

class InMemoryPostRepository : PostRepository {


    private var standartPosts = listOf<Post>(
        Post(
            id = 0,
            ownerName = "Нетология. Университет интернет-профессий",
            text = "Привет, это новая Нетология!\n\nКогда-то Нетология начиналась с интенсивов по " +
                    "онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и " +
                    "управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных " +
                    "профессионалов.\n\nНо самое важное остаётся с нами: мы верим, что в каждом уже " +
                    "есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. " +
                    "Наша миссия — помочь встать на путь роста и начать цепочку перемен → " +
                    "http://netolo.gy/fyb",
            likes = Likes(count = 10, userLikes = false),
            date = 1590086160000,
            reposts = 5,
            views = 5
        ),
        Post(
            id = 1,
            ownerName = "Нетология. Университет интернет-профессий",
            text = "Привет, это новая Нетология!\n\nКогда-то Нетология начиналась с интенсивов по " +
                    "онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и " +
                    "управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных " +
                    "профессионалов.\n\nНо самое важное остаётся с нами: мы верим, что в каждом уже " +
                    "есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. " +
                    "Наша миссия — помочь встать на путь роста и начать цепочку перемен → " +
                    "http://netolo.gy/fyb",
            date = 1600423920000
        )
    )

    private val additionalPosts = List(20) { index ->
        Post(
            id = 2 + index, ownerName = "Нетология. Университет интернет-профессий",
            text = "some text. index of additionalPosts= $index"
        )
    }

    private var posts = standartPosts.plus(additionalPosts)

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likes = Likes(
                    userLikes = !it.likes.userLikes,
                    count = if (it.likes.userLikes) {
                        it.likes.count - 1
                    } else {
                        it.likes.count + 1
                    }
                )
            )
        }
        data.value = posts
    }

    override fun shareById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else {
                it.copy(reposts = it.reposts + 1)
            }
        }
        data.value = posts
    }


}