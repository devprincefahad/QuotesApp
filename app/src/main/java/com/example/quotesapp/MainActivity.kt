package com.example.quotesapp

import android.content.ClipData
import android.content.ClipboardManager
import retrofit2.Callback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.quotesapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var quotes = ArrayList<QuotesResponse>()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: QuotesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.progressBar.visibility = View.VISIBLE

        setRecyclerView()
        fetchData()

    }

    private fun fetchData() {
        val call = QuoteInstance.api.callQuotes()
        call.enqueue(object : Callback<List<QuotesResponse>> {
            override fun onResponse(
                call: Call<List<QuotesResponse>>,
                response: Response<List<QuotesResponse>>
            ) {
                binding.progressBar.visibility = View.GONE
                val data = response.body()
                if (data != null) {
                    quotes.clear()
                    quotes.addAll(data)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<QuotesResponse>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error in fetching news", Toast.LENGTH_LONG)
                    .show()
            }

        })

    }

    private fun setRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapter = QuotesListAdapter(this@MainActivity, quotes, copyListener)
        binding.recyclerView.adapter = adapter
    }

    private val copyListener: CopyListener = object : CopyListener {
        override fun onCopyClicked(text: String) {
            val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("copied_data", text)
            clipBoard.setPrimaryClip(clip)
            Toast.makeText(this@MainActivity, "Quote Copied to Clipboard!", Toast.LENGTH_LONG)
                .show()
        }

    }


}