package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.socialNetwork.Post
import ru.netology.nmedia.socialNetwork.calculations.activitiesCountFormat
import ru.netology.nmedia.socialNetwork.calculations.dateFormatting
import kotlin.properties.Delegates

internal class PostsAdapter(

    //private val onLikeClicked: (Post) -> Unit
private val onButtonOfLikeClicked: (Post) -> Unit,
    private val onButtonOfSharesClicked: (Post) -> Unit
): RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    var posts: List<Post> by Delegates.observable(emptyList()) {_, _, _ -> notifyDataSetChanged()}

    inner class ViewHolder(
        private val binding: PostListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) = with(binding) {
            authorName.text = post.ownerName
            dateOfPost.text = dateFormatting(post.date)
            textBlock.text = post.text
            quantityOfShares.text = post.reposts.toString()
            quantityOfViews.text = post.views.toString()
            //quantityOfLikes.text = activitiesCountFormat(post.likes.count)
            quantityOfLikes.text = post.likes.count.toString()
            buttonOfLikes.setImageResource(
                if (post.likes.userLikes) {
                    ru.netology.nmedia.R.drawable.ic_liked_24
                } else {
                    ru.netology.nmedia.R.drawable.ic_like_24
                }
            )
            buttonOfLikes.setOnClickListener { onButtonOfLikeClicked(post) }
//            buttonOfLikes.setOnClickListener { viewModel.onButtonOfLikeClicked(post.id)
//                println("ButtonOfLike Post ${post.id} clicked")
//            }
            buttonOfShares.setOnClickListener {
                onButtonOfSharesClicked(post)
                //            buttonOfShares.setOnClickListener {
//                viewModel.onButtonOfSharesClicked(post.id)
//                println("ButtonOfShares Post ${post.id} clicked")
//            }
            }
        }

//        fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val inflater = LayoutInflater.from(parent.context)
//            val binding = PostListItemBinding.inflate(inflater, parent, false)
//            return ViewHolder(binding)
//        }
//
//        fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            holder.bind(posts[position])
//        }
//
//        fun getItemCount() = posts.size

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])    }

    override fun getItemCount() = posts.size

}