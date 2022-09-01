package ru.netology.nmedia.db;

import ru.netology.nmedia.dto.Post;

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Int)
    fun shareById(id: Int)
    fun removeById(id: Int)
}