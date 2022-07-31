//package ru.netology.nmedia.ui
//
//import android.content.Intent
//import android.os.Bundle
//import android.text.method.ScrollingMovementMethod
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.PopupMenu
//import androidx.fragment.app.activityViewModels
//import androidx.fragment.app.setFragmentResultListener
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import ru.netology.nmedia.R
//import ru.netology.nmedia.databinding.PostFragmentBinding
//import ru.netology.nmedia.socialNetwork.Post
////import ru.netology.nmedia.util.UrlParse
//import ru.netology.nmedia.socialNetwork.calculations.activitiesCountFormat
//import ru.netology.nmedia.socialNetwork.calculations.dateFormatting
//import ru.netology.nmedia.viewModel.PostViewModel
//
//
//class PostFragmentBrokenCopy : Fragment() {
//
//    private val args by navArgs<PostFragmentArgs>()
//
//    private val viewModel by activityViewModels<PostViewModel>()
//
//    private lateinit var singlePost: Post
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return PostFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
//            with(binding.postFragment) {
//
//                viewModel.data.value?.let { listOfPosts ->
//                    singlePost = listOfPosts.first { post -> post.id == args.postId }
////                    render(singlePost)
//                    //
//                    authorName.text = singlePost.ownerName
//                    dateOfPost.text = dateFormatting(singlePost.date)
//                    textBlock.text = singlePost.text
//                    if (singlePost.video.isNullOrBlank()) {
//                        youtubeLinkPreview.setImageDrawable(null)
//                        youtubeLinkPreview.visibility = View.GONE
//                        youtubeLink.visibility = View.GONE
//                        playButton.visibility = View.GONE
//                    } else {
//                        youtubeLink.text = singlePost.video
//                        youtubeLink.visibility = View.VISIBLE
//                        youtubeLinkPreview.setImageResource(R.drawable.youtube_preview)
//                        youtubeLinkPreview.visibility = View.VISIBLE
//                        playButton.visibility = View.VISIBLE
//                    }
//
//
//                    buttonOfShares.text = singlePost.reposts.toString()
//                    quantityOfViews.text = singlePost.views.toString()
//                    buttonOfLikes.text =
//                        activitiesCountFormat(singlePost.likesCount)
//                    buttonOfLikes.isChecked = singlePost.userLikes
//                    //
//
//                }
//
//                viewModel.data.observe(viewLifecycleOwner) { listOfPosts ->
//                    if (!listOfPosts.any { post -> post.id == args.postId }) {
//                        return@observe
//                    }
//                    if (listOfPosts.isNullOrEmpty()) {
//                        return@observe
//                    }
//                    singlePost = listOfPosts.first { post -> post.id == args.postId }
////                    render(singlePost)
//                    //
//                    authorName.text = singlePost.ownerName
//                    dateOfPost.text = dateFormatting(singlePost.date)
//                    textBlock.text = singlePost.text
//                    if (singlePost.video.isNullOrBlank()) {
//                        youtubeLinkPreview.setImageDrawable(null)
//                        youtubeLinkPreview.visibility = View.GONE
//                        youtubeLink.visibility = View.GONE
//                        playButton.visibility = View.GONE
//                    } else {
//                        youtubeLink.text = singlePost.video
//                        youtubeLink.visibility = View.VISIBLE
//                        youtubeLinkPreview.setImageResource(R.drawable.youtube_preview)
//                        youtubeLinkPreview.visibility = View.VISIBLE
//                        playButton.visibility = View.VISIBLE
//                    }
//
//
//                    buttonOfShares.text = singlePost.reposts.toString()
//                    quantityOfViews.text = singlePost.views.toString()
//                    buttonOfLikes.text =
//                        activitiesCountFormat(singlePost.likesCount)
//                    buttonOfLikes.isChecked = singlePost.userLikes
//                    //
//
//                }
//
//                setFragmentResultListener(requestKey = PostContentFragment.REQUEST_KEY) { requestKey, bundle ->
//                    if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
//                    val newPostContent =
//                        bundle.getString(PostContentFragment.RESULT_KEY)
//                            ?: return@setFragmentResultListener
//                    viewModel.onSaveButtonClicked(newPostContent)
//                }
//
//                viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) { initialContent ->
//                    val direction =
//                        ru.netology.nmedia.ui.PostFragmentDirections.toPostContentFragment( //) .PostFragmentToPostContentFragment(
//                            initialContent,
//                            CALLER_POST
//                        )
//                    findNavController().navigate(direction)
//                }
//
//                buttonOfLikes.setOnClickListener {
//                    viewModel.onButtonOfLikesClicked(singlePost)
//                }
//                buttonOfShares.setOnClickListener {
//                    viewModel.onButtonOfSharesClicked(singlePost)
//                }
//                viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
//                    val intent = Intent().apply {
//                        action = Intent.ACTION_SEND
//                        putExtra(Intent.EXTRA_TEXT, postContent)
//                        type = "text/plain"
//                    }
//                    val shareIntent =
//                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
//                    startActivity(shareIntent)
//                }
//
//                val popupMenu by lazy {
//                    PopupMenu(context, optionsOfPost).apply {
//                        inflate(R.menu.options_post)
//                        setOnMenuItemClickListener { option ->
//                            when (option.itemId) {
//                                R.id.remove -> {
//                                    findNavController().popBackStack()
//                                    viewModel.onRemoveClicked(singlePost)
//                                    true
//                                }
//                                R.id.edit -> {
//                                    viewModel.onEditClicked(singlePost)
//                                    true
//                                }
//                                else -> {
//                                    false
//                                }
//                            }
//                        }
//                    }
//                }
//
////                binding. .postOptionsMaterialButton.setOnClickListener {
////                    popupMenu.show()
////                }
//
//            }
//        }.root
//    }
//
//
////    private fun PostFragment.render(post: Post) {
////
////        authorNameTextView.text = post.author
////        postText.text = post.content
////        postText.movementMethod = ScrollingMovementMethod()
////        dateAndTimeTextView.text = post.published
////        likesMaterialButton.text = post.likes.formatNumber()
////        likesMaterialButton.isChecked = post.likedByMe
////        shareMaterialButton.text = post.shares.formatNumber()
////        shareMaterialButton.isChecked = post.sharedByMe
////        postViews.text = post.views.toString()
////
////        val urlList = UrlParse.getHyperLinks(postText.text.toString())
////        for (link in urlList) {
////            if (link.contains("youtube")
////                ||
////                link.contains("youtu.be")
////            ) {
////                post.videoUrl = link
////            } else {
////                post.videoUrl = ""
////            }
////        }
////        if (post.videoUrl.isEmpty()) {
////            videoContentGroup.visibility = View.GONE
////        } else {
////            videoContentGroup.visibility = View.VISIBLE
////        }
////    }
//
//    companion object {
//        const val CALLER_POST = "CallerPost"
//    }
//
//}