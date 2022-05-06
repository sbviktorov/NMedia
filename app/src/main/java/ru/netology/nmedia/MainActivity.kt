package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
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

        val currentPost = Post(
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

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.greetings.isVisible = false

        viewModel.data.observe(this) {
            binding.nestedPost.configure(currentPost)
        }

        binding.nestedPost.buttonOfLikes.setOnClickListener {
            viewModel.onButtonOfLikeClicked()
        }

        binding.nestedPost.buttonOfShares.setOnClickListener {
            viewModel.onButtonOfSharesClicked()
        }
    }

    private fun PostListItemBinding.configure(post: Post) {
        authorName.text = viewModel.data.value?.ownerName
        dateOfPost.text = viewModel.data.value?.date?.let { dateFormatting(it) }
        textBlock.text = viewModel.data.value?.text
        quantityOfShares.text = viewModel.data.value?.reposts.toString()
        quantityOfViews.text = viewModel.data.value?.views.toString()
        quantityOfLikes.text =
            viewModel.data.value?.likes?.let { it1 -> activitiesCountFormat(it1.count) }
        buttonOfLikes.setImageResource(
            if (viewModel.data.value?.likes?.userLikes == true) {
                R.drawable.ic_liked_24
            } else {
                R.drawable.ic_like_24
            }
        )
    }
}