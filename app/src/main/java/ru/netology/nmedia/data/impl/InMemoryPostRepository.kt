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
            text = "Задача YouTube Video",
            likes = Likes(count = 0, userLikes = false),
            reposts = 0,
            views = 0,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
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
            likes = Likes(count = 10, userLikes = false),
            date = 1590086160000,
            reposts = 5,
            views = 5
        ),
        Post(
            id = nextID++,
            ownerName = ownerName,
            text = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных " +
                    "приложений, учимся рассказывать истории и составлять PR-стратегию прямо на " +
                    "бесплатных занятиях \uD83D\uDC47\n" +
                    "\n" +
                    "▫ 24 сентября стартует новый набор курса «Диджитал-старт: первый шаг к " +
                    "востребованной профессии»\n" +
                    "Познакомимся с разнообразием современных профессий, разберёмся, какая " +
                    "специальность подходит именно вам и построим пошаговый план профессионального " +
                    "развития → http://netolo.gy/fPM\n" +
                    "\n" +
                    "▫ 21 сентября в 16.00 — Разработка мобильных приложений на IOS и Android: с " +
                    "чего начать?\n" +
                    "Разбираемся, в какой сфере выгоднее и легче создать мобильное приложение и " +
                    "можно ли создать и запустить продукт самостоятельно → http://netolo.gy/fPP\n" +
                    "\n" +
                    "▫ 22 сентября в 15.00 — Сторителлинг: как продавать с помощью истории\n" +
                    "Поговорим о том, что такое сторителлинг, как создать историю, которая будет " +
                    "интересна аудитории и как использовать истории в продажах → " +
                    "http://netolo.gy/fPQ\n" +
                    "\n" +
                    "▫ 23 сентября в 19.00 — Как разработать PR‑стратегию: лучшие практики\n" +
                    "На реальных примерах познакомимся с основами создания PR-стратегии, разберёмся," +
                    " что в неё входит и как это влияет на доходы → http://netolo.gy/fPS\n" +
                    "\n" +
                    "▫ 24 сентября в 16.00 — Что делает бизнес-аналитик и как им стать\n" +
                    "Узнаем, кому и зачем нужна бизнес-аналитика, как такой специалист может " +
                    "помочь компании и какими компетенциями нужно обладать для старта в профессии " +
                    "→ http://netolo.gy/fPT",
            date = 1600423920000,
            likes = Likes(1,true)
        )
    )

    private val additionalPosts = List(20) {
        Post(
            id = nextID++,
            ownerName = ownerName,
            text = "some text. id of additionalPosts= ${nextID - 1}",
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
            post.copy(
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

    override fun cancelUpdate() {
        data.value = posts
    }
//
//    private fun editPost(post: Post) {
//        posts = posts.map { if (it.id == post.id) post else it }
//        data.value = posts
//    }
}