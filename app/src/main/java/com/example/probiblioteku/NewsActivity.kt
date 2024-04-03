package com.example.probiblioteku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.io.IOException

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

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
            // Запуск корутины для выполнения сетевого запроса
            withContext(Dispatchers.IO) {
                // URL сайта для парсинга
                val url = "https://lib.ugrasu.ru/"

                try {
                    // Получение объекта Document, представляющего веб-страницу
                    val document = Jsoup.connect(url).get()

                    // Получение заголовка сайта (тег <title>)
                    val title = document.title()

                    // Вывод заголовка в консоль
                    println("Заголовок сайта: $title")
                } catch (e: IOException) {
                    // Обработка ошибок, связанных с подключением к сайту
                    println("Ошибка при подключении к сайту: ${e.message}")
                }
            }
        }

    }
}