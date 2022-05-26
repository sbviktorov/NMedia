package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
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
        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                val isContent = viewModel.onSaveButtonClicked(content)
                if (!isContent) Toast.makeText(
                    applicationContext,
                    context.getString(R.string.empty_content),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.cancelEditButton.setOnClickListener {
            viewModel.onCancelEditButtonClicked()
        }
        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.text
                setText(content)
                if (content != null) {
                    binding.lastContent.text = currentPost.text
                    binding.editToolGroup.visibility = View.VISIBLE
                    requestFocus()
                } else {
                    clearFocus()
                    binding.editToolGroup.visibility = View.GONE
                    hideKeyboard()
                }
            }
        }
        viewModel.sharePostContent.observe(this){postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent) //post.text
                type = "text/plain"
            }

            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }
    }
}



