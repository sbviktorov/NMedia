package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
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
               //viewModel.onSaveButtonClicked(content)
                val isContent = viewModel.onSaveButtonClicked(content)
                if (!isContent) Toast.makeText(applicationContext, context.getString(R.string.empty_content), Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.text
                setText(content) //эта хрень как-то сбрасывает пост
                if (content != null) {
                    requestFocus()
                    //showKeyboard()
                } else {
                    clearFocus()
                    hideKeyboard()
                }
            }

        }
    }
}



