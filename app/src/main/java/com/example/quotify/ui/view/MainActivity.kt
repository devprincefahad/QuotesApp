package com.example.quotify.ui.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quotify.R
import com.example.quotify.api.QuoteInstance
import com.example.quotify.databinding.ActivityMainBinding
import com.example.quotify.model.Result
import com.example.quotify.repository.QuoteRepository
import com.example.quotify.ui.viewmodel.QuoteViewModelFactory
import com.example.quotify.ui.viewmodel.QuotesViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quotesViewModel: QuotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.progressBar.visibility = View.VISIBLE
        binding.progressBar.getIndeterminateDrawable()
            .setColorFilter(
                ContextCompat.getColor(this, R.color.pink),
                PorterDuff.Mode.SRC_IN
            )

        val repository = QuoteRepository(QuoteInstance.api)
        quotesViewModel = ViewModelProvider(
            this,
            QuoteViewModelFactory(repository)
        ).get(QuotesViewModel::class.java)

        val anim1 = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        binding.textView.animation = anim1

        anim1.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                //
            }

            override fun onAnimationEnd(animation: Animation?) {

                val anim2 = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
                binding.relativeLayout.startAnimation(anim2)
                binding.relativeLayout.visibility = View.VISIBLE
                binding.floatingActionButtonCopy.visibility = View.VISIBLE
                binding.floatingActionButtonShare.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    quotesViewModel.quote.observe(this@MainActivity, Observer {
                        updateText(it)
                    })
                }, 4000)

            }

            override fun onAnimationRepeat(animation: Animation?) {
                TODO("Not yet implemented")
            }

        })

    }

    fun updateText(result: Result) {
        binding.quoteText.text = result.content
        binding.quoteAuthor.text = result.author
        binding.progressBar.visibility = View.GONE

    }

    fun onNext(view: android.view.View) {
        binding.progressBar.visibility = View.GONE
        quotesViewModel.onNext()
    }

    fun onPrevious(view: android.view.View) {
        binding.progressBar.visibility = View.GONE
        quotesViewModel.onPrevious()
    }


    fun onShare(view: android.view.View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, quotesViewModel.quote.value?.content)
        startActivity(intent)
    }

    fun onCopy(view: android.view.View) {
        val value = quotesViewModel.quote.value?.content
        val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("copied_data", value)
        clipBoard.setPrimaryClip(clip)
        Toast.makeText(this@MainActivity, "Quote Copied to Clipboard!", Toast.LENGTH_SHORT)
            .show()
    }

}