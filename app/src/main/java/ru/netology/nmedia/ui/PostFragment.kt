package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.PostFragmentBinding
import ru.netology.nmedia.socialNetwork.Post
import ru.netology.nmedia.socialNetwork.calculations.activitiesCountFormat
import ru.netology.nmedia.socialNetwork.calculations.dateFormatting
import ru.netology.nmedia.viewModel.PostViewModel


class PostFragment : Fragment() {
    private val args by navArgs<PostFragmentArgs>()

    private val viewModel by viewModels<PostViewModel>()

    private lateinit var selectedPost: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        setFragmentResultListener(requestKey = PostContentFragment.REQUEST_KEY) { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(
                PostContentFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newPostContent)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this) { initialContent ->
            val direction = FeedFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(direction)
        }
    }
//        override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//        ) = PostFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
//
//            val adapter =
//                PostsAdapter(viewModel)
//            viewModel.data.observe(viewLifecycleOwner) { posts ->
//                adapter.submitList(posts)
//            }
//
//        }.root

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        with(binding.postFragment) {
//    ): View {
//        return PostFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
//            with(binding.postFragment) {
            val selectedPostID = args.postId

            val postPopupMenu by lazy {
                PopupMenu(root.context, optionsOfPost).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                viewModel.onRemoveClicked(selectedPost)
                                findNavController().popBackStack()
                                true
                            }
                            R.id.edit -> {
                                viewModel.onEditClicked(selectedPost)
                                true
                            }
                            else -> false
                        }
                    }
                }
            }
            val adapter =
                PostsAdapter(viewModel)

//                viewModel.data.value?.let { posts ->
//                    selectedPost = posts.first { it.id == selectedPostID }
//
//
//                }
            viewModel.data.observe(viewLifecycleOwner) { posts ->
//                adapter.submitList(posts)
                if (!posts.any { post -> post.id == args.postId }) {
                    return@observe
                }
                if (posts.isNullOrEmpty()) {
                    return@observe
                }
                selectedPost = posts.first { it.id == selectedPostID }
//                adapter.submitList(selectedPost)
//
//                    render(selectedPost)
                authorName.text = selectedPost.ownerName
                dateOfPost.text = dateFormatting(selectedPost.date)
                textBlock.text = selectedPost.text
                if (selectedPost.video.isNullOrBlank()) {
                    youtubeLinkPreview.setImageDrawable(null)
                    youtubeLinkPreview.visibility = View.GONE
                    youtubeLink.visibility = View.GONE
                    playButton.visibility = View.GONE
                } else {
                    youtubeLink.text = selectedPost.video
                    youtubeLink.visibility = View.VISIBLE
                    youtubeLinkPreview.setImageResource(R.drawable.youtube_preview)
                    youtubeLinkPreview.visibility = View.VISIBLE
                    playButton.visibility = View.VISIBLE
                }


                buttonOfShares.text = selectedPost.reposts.toString()
                quantityOfViews.text = selectedPost.views.toString()
                buttonOfLikes.text =
                    activitiesCountFormat(selectedPost.likesCount)
                buttonOfLikes.isChecked = selectedPost.userLikes

            }
            buttonOfLikes.setOnClickListener {
                viewModel.onButtonOfLikesClicked(
                    selectedPost
                )
            }
            buttonOfShares.setOnClickListener {
                viewModel.onButtonOfSharesClicked(selectedPost)
            }
            optionsOfPost.setOnClickListener { postPopupMenu.show() }
            playButton.setOnClickListener {
                viewModel.onPlayButtonClicked(selectedPost)
            }
            youtubeLink.setOnClickListener {
                viewModel.onPlayButtonClicked(selectedPost)
            }
            youtubeLinkPreview.setOnClickListener {
                viewModel.onPlayButtonClicked(selectedPost)
            }

        }

    }.root
}




//        private fun PostFragmentBinding.render(post: Post) {
//
//            postFragment.authorName.text = post.ownerName
//            postFragment.dateOfPost.text = dateFormatting(post.date)
//            postFragment.textBlock.text = post.text
//            if (post.video.isNullOrBlank()) {
//                postFragment.youtubeLinkPreview.setImageDrawable(null)
//                postFragment.youtubeLinkPreview.visibility = View.GONE
//                postFragment.youtubeLink.visibility = View.GONE
//                postFragment.playButton.visibility = View.GONE
//            } else {
//                postFragment.youtubeLink.text = post.video
//                postFragment.youtubeLink.visibility = View.VISIBLE
//                postFragment.youtubeLinkPreview.setImageResource(R.drawable.youtube_preview)
//                postFragment.youtubeLinkPreview.visibility = View.VISIBLE
//                postFragment.playButton.visibility = View.VISIBLE
//            }
//
//
//            postFragment.buttonOfShares.text = post.reposts.toString()
//            postFragment.quantityOfViews.text = post.views.toString()
//            postFragment.buttonOfLikes.text =
//                activitiesCountFormat(post.likesCount)
//            postFragment.buttonOfLikes.isChecked = post.userLikes
//        }


