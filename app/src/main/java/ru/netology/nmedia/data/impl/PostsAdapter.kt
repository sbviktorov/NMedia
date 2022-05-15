package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.socialNetwork.Post
import ru.netology.nmedia.socialNetwork.calculations.dateFormatting

typealias OnPostButtonClicked = (Post) -> Unit

internal class PostsAdapter(

    private val onButtonOfLikeClicked: OnPostButtonClicked,
    private val onButtonOfSharesClicked: OnPostButtonClicked
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(
        private val binding: PostListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var post: Post

        init {
            binding.buttonOfLikes.setOnClickListener { onButtonOfLikeClicked(post) }
            binding.buttonOfShares.setOnClickListener {
                onButtonOfSharesClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.ownerName
                dateOfPost.text = dateFormatting(post.date)
                textBlock.text = post.text
                quantityOfShares.text = post.reposts.toString()
                quantityOfViews.text = post.views.toString()
                quantityOfLikes.text = post.likes.count.toString()
                buttonOfLikes.setImageResource(
                    if (post.likes.userLikes) {
                        R.drawable.ic_liked_24
                    } else {
                        R.drawable.ic_like_24
                    }
                )
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
