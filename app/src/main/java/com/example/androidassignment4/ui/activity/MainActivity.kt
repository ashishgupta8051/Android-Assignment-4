package com.example.androidassignment4.ui.activity

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidassignment4.R
import com.example.androidassignment4.adapter.ArticleAdapter
import com.example.androidassignment4.databinding.ActivityMainBinding
import com.example.androidassignment4.model.Article
import com.example.androidassignment4.repository.NewsRepository
import com.example.androidassignment4.utils.ArticleOnClick
import com.example.androidassignment4.viewmodel.NewsViewModel
import com.example.androidassignment4.viewmodelfactory.NewsViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity(),ArticleOnClick {
    private val newsRepo = NewsRepository()
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val intent = Intent(this,FirebaseMessaging::class.java)
//        startService(intent)
        getDeviceToken()
        val viewModelFactory = NewsViewModelFactory(newsRepo)
        newsViewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        setRecyclerView()
        observe()

    }

    private fun setRecyclerView() {
        articleAdapter = ArticleAdapter(this)
        binding.rvArticle.adapter = articleAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        newsViewModel.articleList.observe(this) { articleList ->
            if (articleList.isNotEmpty()){
                articleAdapter.getArticle(articleList)
                articleAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onClickArticle(article: Article) {
        openUrlInChrome(article.url)
    }

    private fun openUrlInChrome(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val defaultBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(defaultBrowserIntent)
        }
    }

    private fun getDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("TOKEN","Error")
                return@OnCompleteListener
            }

            val token = task.result
            Log.e("TOKEN",token.toString())

        })
    }

}