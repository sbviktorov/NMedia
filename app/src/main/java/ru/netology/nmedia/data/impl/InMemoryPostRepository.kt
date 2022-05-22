package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.socialNetwork.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.socialNetwork.objects.Likes

class InMemoryPostRepository : PostRepository {

    private val ownerName = "Нетология. Университет интернет-профессий"

    private var nextID = 0

    private var standartPosts = listOf<Post>(
        Post(
            id = nextID++,
            ownerName = ownerName,
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
            id = nextID++,
            ownerName = ownerName,
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

    private val additionalPosts = List(20) {
        Post(
            //id = 2 + index,
            id = nextID++,
            ownerName = ownerName,
            text = "some text. id of additionalPosts= ${nextID-1}",
            likes = Likes(
                count = listOf<Int>(
                    0,
                    999,
                    1_099,
                    9_999,
                    999_999,
                    1_199_999,
                    1_999_999,
                    9_999_999
                ).random()
            )

        )
    }

    private var posts = standartPosts.plus(additionalPosts)

    //private var nextID = posts.last().id + 1

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(postId: Int) {
        posts = posts.map {
            if (it.id != postId) it else it.copy(
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

    override fun shareById(postId: Int) {
        posts = posts.map {
            if (it.id != postId) it else {
                it.copy(reposts = it.reposts + 1)
            }
        }
        data.value = posts
    }

    override fun deleteById(postId: Int) {
        posts = posts.filterNot { it.id == postId }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.newPostID) insert(post) else update(post)
    }



    private fun insert(post: Post) {
        posts = listOf<Post>().plus(
//            Post(
//            id = nextID++,
//            ownerName = ownerName,
//            text =post.text))
            post.copy(
                //text = post.text.plus(" id = $nextID"),
                id = nextID++
            )
        )
            .plus(posts)
        data.value = posts

    }

    private fun update(post: Post) {
        posts = posts.map { if (it.id == post.id) post else it }
        data.value = posts


    }


}