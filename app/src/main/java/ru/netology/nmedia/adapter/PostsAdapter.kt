package ru.netology.nmedia.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.socialNetwork.Post
import ru.netology.nmedia.socialNetwork.calculations.activitiesCountFormat
import ru.netology.nmedia.socialNetwork.calculations.dateFormatting
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(
        private val binding: PostListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.optionsOfPost).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            interactionListener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            interactionListener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.buttonOfLikes.setOnClickListener {
                interactionListener.onButtonOfLikesClicked(
                    post
                )
            }
            binding.buttonOfShares.setOnClickListener {
                interactionListener.onButtonOfSharesClicked(post)
            }
            binding.optionsOfPost.setOnClickListener { popupMenu.show() }
            binding.playButton.setOnClickListener {
                interactionListener.onPlayButtonClicked(post)
            }
            binding.youtubeLink.setOnClickListener {
                interactionListener.onPlayButtonClicked(post)
            }
            binding.youtubeLinkPreview.setOnClickListener {
                interactionListener.onPlayButtonClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.ownerName
                dateOfPost.text = dateFormatting(post.date)
                textBlock.text = post.text
//                youtubeLinkPreview.setImageResource()
                if (post.video.isNullOrBlank()) {
                    youtubeLinkPreview.setImageDrawable(null)
                    youtubeLinkPreview.visibility = View.GONE
                    youtubeLink.visibility = View.GONE
                    playButton.visibility = View.GONE
                } else {
                    youtubeLink.text = post.video
                    youtubeLink.visibility = View.VISIBLE
                    youtubeLinkPreview.setImageResource(R.drawable.youtube_preview)
                    youtubeLinkPreview.visibility = View.VISIBLE
                    playButton.visibility = View.VISIBLE
                }

                buttonOfShares.text = post.reposts.toString()
                quantityOfViews.text = post.views.toString()
                buttonOfLikes.text =
                    activitiesCountFormat(post.likes.count)
                buttonOfLikes.isChecked = post.likes.userLikes

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }

}
