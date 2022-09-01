package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.dto.Post

class SQLiteRepository(
    private val dao: PostDao
) : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(dao.getAll())

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        data.value = if (id == 0) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
    }

    override fun like(postId: Int) {
        dao.likeById(postId)
        data.value = posts.map {
            if (it.id != postId) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
    }

    override fun share(postId: Int) {
        dao.shareById(postId)
        data.value = posts.map {
            if (it.id != postId) it else it.copy(
                shareCount = it.shareCount + 1
            )
        }
    }

    override fun delete(postId: Int) {
        dao.removeById(postId)
        data.value = posts.filter { it.id != postId }
    }
}