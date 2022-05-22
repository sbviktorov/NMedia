package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import ru.netology.nmedia.data.impl.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.socialNetwork.Post
import ru.netology.nmedia.socialNetwork.calculations.activitiesCountFormat
import ru.netology.nmedia.socialNetwork.calculations.dateFormatting
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val currentPost = Post(
//            id = 0,
//            ownerName = "Нетология. Университет интернет-профессий",
//            text = "Привет, это новая Нетология!\n\nКогда-то Нетология начиналась с интенсивов по " +
//                    "онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и " +
//                    "управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных " +
//                    "профессионалов.\n\nНо самое важное остаётся с нами: мы верим, что в каждом уже " +
//                    "есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. " +
//                    "Наша миссия — помочь встать на путь роста и начать цепочку перемен → " +
//                    "http://netolo.gy/fyb"
//        )

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.greetings.isVisible = false

//        val viewModel: PostViewModel by viewModels()
//        val adapter = PostsAdapter {
//
//        }
        val adapter = PostsAdapter(viewModel::onButtonOfLikeClicked, viewModel::onButtonOfSharesClicked)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)}



            //binding.nestedPost.configure(currentPost)
//            posts.map { post ->
//
//                PostListItemBinding.inflate(layoutInflater, binding.container, true)
//                    .apply {
//                        authorName.text = post.ownerName
//                        dateOfPost.text = post.date.let { dateFormatting(it) }
//                        textBlock.text = post.text
//                        quantityOfShares.text = post.reposts.toString()
//                        quantityOfViews.text = post.views.toString()
//                        quantityOfLikes.text = activitiesCountFormat(post.likes.count)
//                        buttonOfLikes.setImageResource(
//                            if (post.likes.userLikes) {
//                                R.drawable.ic_liked_24
//                            } else {
//                                R.drawable.ic_like_24
//                            }
//                        )
//                        buttonOfLikes.setOnClickListener { viewModel.onButtonOfLikeClicked(post.id)
//                            println("ButtonOfLike Post ${post.id} clicked")}
//                        buttonOfShares.setOnClickListener {
//                            viewModel.onButtonOfSharesClicked(post.id)
//                            println("ButtonOfShares Post ${post.id} clicked")
//                        }
                    //}.root


//                binding.nestedPost.buttonOfLikes.setOnClickListener {
//                    viewModel.onButtonOfLikeClicked()
//                }
//
//                binding.nestedPost.buttonOfShares.setOnClickListener {
//                    viewModel.onButtonOfSharesClicked()
//                }
            }//.forEach{
            //binding.container.addView(it)
        }



