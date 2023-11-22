package com.example.androidassignment4.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment4.databinding.ArticleListItemBinding
import com.example.androidassignment4.model.Article
import com.example.androidassignment4.utils.ArticleOnClick

class ArticleAdapter(private val articleOnClick: ArticleOnClick) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private val articleList: MutableList<Article> = mutableListOf()

    inner class ArticleViewHolder(val binding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val binding = holder.binding
        val article = articleList[position]

        binding.txtAuthor.text = "Author: "+article.author
        binding.txtTitle.text = "Title: "+article.title
        binding.txtDescription.text = "Description: "+article.description

        binding.root.setOnClickListener{
            articleOnClick.onClickArticle(article)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun getArticle(list:List<Article>){
        articleList.clear()
        articleList.addAll(list)
        notifyDataSetChanged()
    }

}