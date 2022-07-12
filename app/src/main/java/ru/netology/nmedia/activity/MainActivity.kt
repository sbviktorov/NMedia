package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter =
            PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent) //post.text
                type = "text/plain"
            }

            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }
        viewModel.youtubeURL.observe(this) { youtubeURL ->
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(youtubeURL)
                )
            )
        }

        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent: String? ->
            postContent?.let(viewModel::onSaveButtonClicked) ?: return@registerForActivityResult
        }
        viewModel.navigateToPostContentScreenEvent.observe(this) {
            postContentActivityLauncher.launch(it)
        }
    }
}



