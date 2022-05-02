package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.socialNetwork.Post
import ru.netology.nmedia.socialNetwork.calculations.activitiesCountFormat
import ru.netology.nmedia.socialNetwork.calculations.dateFormatting


class MainActivity : AppCompatActivity() {
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
        binding.nestedPost.configure(currentPost)

        binding.root.setOnClickListener {
            println("root clicked")
        }
        binding.nestedPost.buttonOfLikes.setOnClickListener {
            println("buttonOfLikes clicked")
            if (!currentPost.likes.userLikes) {
                binding.nestedPost.buttonOfLikes.setImageResource(R.drawable.ic_liked_24)
                currentPost.likes.count++
                binding.nestedPost.quantityOfLikes.text =
                    activitiesCountFormat(currentPost.likes.count)
                currentPost.likes.userLikes = !currentPost.likes.userLikes
            } else {
                binding.nestedPost.buttonOfLikes.setImageResource(R.drawable.ic_like_24)
                currentPost.likes.count--
                currentPost.likes.userLikes = !currentPost.likes.userLikes
            }
            binding.nestedPost.quantityOfLikes.text = activitiesCountFormat(currentPost.likes.count)

        }
        binding.nestedPost.buttonOfShares.setOnClickListener {
            println("buttonOfShares clicked")
            currentPost.reposts++
            binding.nestedPost.quantityOfShares.text = currentPost.reposts.toString()
        }


    }

    private fun PostListItemBinding.configure(post: Post) {
        quantityOfLikes.text = activitiesCountFormat(post.likes.count)
        authorName.text = post.ownerName
        dateOfPost.text = dateFormatting(post.date)
        textBlock.text = post.text
        quantityOfShares.text = post.reposts.toString()
        quantityOfViews.text = post.views.toString()
    }
}