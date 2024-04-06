package com.example.probiblioteku

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        // navbar
        val buttonProfile: ImageView = findViewById(R.id.buttonProfile)
        buttonProfile.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val buttonBooking: ImageView = findViewById(R.id.buttonBooking)
        buttonBooking.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }
        val buttonNews: ImageView = findViewById(R.id.buttonNews)
        buttonNews.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        runBlocking {
            withContext(Dispatchers.IO) {
                val url = "https://lib.ugrasu.ru/"
                val document = Jsoup.connect(url).get()
                val titles = document.select(".news-title")
                val dates = document.select(".news-date-time")
                val urlImages = document.select(".preview_picture")

                val newsList = mutableListOf<News>()
                for (i in 0 until 8)
                {
                    val title = titles[i].text() + "\n" +  "\n" +dates[i].text()
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