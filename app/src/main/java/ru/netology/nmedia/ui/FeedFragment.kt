package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class FeedFragment : Fragment() {
//class MainActivity : AppCompatActivity() {



    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)

//        val adapter =
//            PostsAdapter(viewModel)
//        binding.postsRecyclerView.adapter = adapter
//        viewModel.data.observe(this) { posts ->
//            adapter.submitList(posts)
//        }
//        binding.fab.setOnClickListener {
//            viewModel.onAddClicked()
//        }

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

        setFragmentResultListener(requestKey = PostContentFragment.REQUEST_KEY) {
            requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(PostContentFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newPostContent)
        }
//        val postContentActivityLauncher = registerForActivityResult(
//            PostContentFragment.ResultContract
//        ) { postContent: String? ->
//            postContent?.let(viewModel::onSaveButtonClicked) ?: return@registerForActivityResult
//        }
        viewModel.navigateToPostContentScreenEvent.observe(this) { initialContent ->
//            postContentActivityLauncher.launch(it)
            parentFragmentManager.commit {
                val fragment = PostContentFragment.create(initialContent)
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(null)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ActivityMainBinding.inflate(layoutInflater, container, false).also {binding ->

        val adapter =
            PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }
    //        return super.onCreateView(inflater, container, savedInstanceState)
    }.root
}



