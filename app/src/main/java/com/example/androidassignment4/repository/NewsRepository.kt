package com.example.androidassignment4.repository

import android.util.Log
import com.example.androidassignment4.model.Article
import com.example.androidassignment4.model.Source
import com.example.androidassignment4.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL


class NewsRepository {

    suspend fun fetchNews(): List<Article> = withContext(Dispatchers.IO) {
        try {
            val response = URL(Constant.News_URL).readText()
            return@withContext parseJson(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList()
        }
    }

    private fun parseJson(jsonString: String): List<Article> {
        val newsList = mutableListOf<Article>()

        try {
            val jsonObject = JSONObject(jsonString)
            val articlesArray = jsonObject.getJSONArray("articles")

            for (i in 0 until articlesArray.length()) {
                val articleObj = articlesArray.getJSONObject(i)

                val sourceObj = articleObj.getJSONObject("source")
                val sourceId = sourceObj.getString("id")
                val sourceName = sourceObj.getString("name")
                val source = Source(sourceId,sourceName)

                val author = articleObj.getString("author")
                val title = articleObj.getString("title")
                val description = articleObj.getString("description")
                val url = articleObj.getString("url")
                val urlToImage = articleObj.getString("urlToImage")
                val publishedAt = articleObj.getString("publishedAt")
                val content = articleObj.getString("content")

                val article = Article(author,content,description,publishedAt,source,title,url,urlToImage)

                newsList.add(article)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }

        return newsList
    }
}
