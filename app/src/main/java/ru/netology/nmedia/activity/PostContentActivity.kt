package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostContentActivityBinding

class PostContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PostContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var textContent = ""
        if (intent.hasExtra(POST_CONTENT_KEY)) {
            textContent = intent.getStringExtra(POST_CONTENT_KEY).toString()
        }
        binding.edit.setText(textContent)
        binding.edit.requestFocus()


        binding.ok.setOnClickListener {
            val intent = Intent()
            val text = binding.edit.text
            if (text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
                android.widget.Toast.makeText(
                    applicationContext, ru.netology.nmedia.R.string.empty_content,
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else {
                val content = text.toString()
                intent.putExtra(RESULT_KEY, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }

    //    object ResultContract : ActivityResultContract<Unit, String?>() {
//        override fun createIntent(context: Context, input: Unit): Intent =
//            Intent(context, PostContentActivity::class.java)
    object ResultContract : ActivityResultContract<String?, String?>() {
        override fun createIntent(context: Context, input: String?): Intent {
            val intent = Intent(context, PostContentActivity::class.java)
            if (input != null) {
                intent.putExtra(POST_CONTENT_KEY, input)
            }
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?) =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(RESULT_KEY)
            } else null
    }

    private companion object {
        private const val RESULT_KEY = "postNewContent"
        private const val POST_CONTENT_KEY = "postContent"
    }
}
