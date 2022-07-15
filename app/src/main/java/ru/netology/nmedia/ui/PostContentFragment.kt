package ru.netology.nmedia.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostContentActivityBinding

class PostContentFragment
//    (
//    private val initialContent: String?
//)
    : Fragment() {

    private val initialContent
        get()= requireArguments().getString(INITIAL_CONTENT_ARGUMENTS_KEY)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = PostContentActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        var textContent = ""
//        if (intent.hasExtra(POST_CONTENT_KEY)) {
//            textContent = intent.getStringExtra(POST_CONTENT_KEY).toString()
//        }
//        binding.edit.setText(textContent)
//        binding.edit.requestFocus()
//
//
//        binding.ok.setOnClickListener {
//            val intent = Intent()
//            val text = binding.edit.text
//            if (text.isNullOrBlank()) {
//                setResult(Activity.RESULT_CANCELED, intent)
//                android.widget.Toast.makeText(
//                    applicationContext, ru.netology.nmedia.R.string.empty_content,
//                    android.widget.Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                val content = text.toString()
//                intent.putExtra(RESULT_KEY, content)
//                setResult(Activity.RESULT_OK, intent)
//            }
//            finish()
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostContentActivityBinding.inflate(layoutInflater, container, false).also { binding ->
//        var textContent = ""
//        if (intent.hasExtra(POST_CONTENT_KEY)) {
//            textContent = intent.getStringExtra(POST_CONTENT_KEY).toString()
//        }

//        binding.edit.setText(textContent)
        binding.edit.setText(initialContent)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: PostContentActivityBinding) {
//        val intent = Intent()
        val text = binding.edit.text
        if (!text.isNullOrBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putString(RESULT_KEY, text.toString())
            setFragmentResult(REQUEST_KEY, resultBundle)
        }
//        if (text.isNullOrBlank()) {
//            setResult(Activity.RESULT_CANCELED, intent)
//            Toast.makeText(
//                applicationContext, R.string.empty_content,
//                Toast.LENGTH_SHORT
//            ).show()
//        } else {
//            val content = text.toString()
//            intent.putExtra(RESULT_KEY, content)
//            setResult(Activity.RESULT_OK, intent)
//        }
        parentFragmentManager.popBackStack()
    }

//    object ResultContract : ActivityResultContract<String?, String?>() {
//        override fun createIntent(context: Context, input: String?): Intent {
//            val intent = Intent(context, PostContentActivity::class.java)
//            if (input != null) {
//                intent.putExtra(POST_CONTENT_KEY, input)
//            }
//            return intent
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?) =
//            if (resultCode == Activity.RESULT_OK) {
//                intent?.getStringExtra(RESULT_KEY)
//            } else null
//    }

    companion object {
        private const val INITIAL_CONTENT_ARGUMENTS_KEY = "initialContent"
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "postNewContent"
        const val POST_CONTENT_KEY = "postContent"

        fun create(initialContent: String?) = PostContentFragment().apply {
            arguments =
                Bundle(1).also { it.putString(INITIAL_CONTENT_ARGUMENTS_KEY, initialContent) }
        }
    }
}
