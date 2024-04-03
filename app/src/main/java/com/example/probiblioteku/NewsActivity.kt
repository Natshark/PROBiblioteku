package com.example.probiblioteku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.LinearLayoutManager
import org.jsoup.Jsoup

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        runBlocking {
            withContext(Dispatchers.IO) {
                val url = "https://lib.ugrasu.ru/"
                val document = Jsoup.connect(url).get()
                val titles = document.select(".news-title")
                val urlImages = document.select(".preview_picture")

                val newsList = mutableListOf<News>()
                for (i in 0 until 4)
                {
                    val title = titles[i].text()
                    val imageSrc = "https://lib.ugrasu.ru${urlImages[i].attr("src")}"
                    val href = "https://lib.ugrasu.ru${titles[i].select("a").first()?.attr("href")}"
                    newsList.add(News(title, imageSrc, href))
                }

                val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
                val adapter = NewsAdapter(newsList)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@NewsActivity)
            }
        }

    }
}