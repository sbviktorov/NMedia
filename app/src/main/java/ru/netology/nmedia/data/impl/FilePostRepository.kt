package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.socialNetwork.Post
import ru.netology.nmedia.data.PostRepository
import kotlin.properties.Delegates

class FilePostRepository(
    private val application: Application
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )
    private val ownerName = "Нетология. Университет интернет-профессий"

    private var nextID: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue) }
    }

    private var standardPosts = listOf<Post>(
        Post(
            id = nextID++,
            ownerName = ownerName,
            text = "Задача YouTube Video",
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
            likesCount = 10,
            userLikes = false,
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
            likesCount = 1,
            userLikes = true
        )
    )

    private var posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value) {
            application.openFileOutput(
                FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }

    private val data: MutableLiveData<List<Post>>


    init {
        val postsFile = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if (postsFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use {
                gson.fromJson(it, type)
            }
        } else standardPosts
        data = MutableLiveData(posts)
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(postId: Long) {
        posts = posts.map {
            if (it.id != postId) it else it.copy(

                userLikes = !it.userLikes,
                likesCount = if (it.userLikes) {
                    it.likesCount - 1
                } else {
                    it.likesCount + 1
                }
            )
        }
    }

    override fun shareById(postId: Long) {
        posts = posts.map {
            if (it.id != postId) it else {
                it.copy(reposts = it.reposts + 1)
            }
        }
    }

    override fun deleteById(postId: Long) {
        posts = posts.filterNot { it.id == postId }
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
    }

    private fun update(post: Post) {
        posts = posts.map { if (it.id == post.id) post else it }
    }

    override fun cancelUpdate() {
    }

    private companion object {
        const val NEXT_ID_PREFS_KEY = "posts"
        const val FILE_NAME = "posts.json"
    }
}