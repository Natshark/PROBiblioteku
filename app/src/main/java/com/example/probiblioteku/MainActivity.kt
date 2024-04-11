package com.example.probiblioteku

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity()
{
    fun check_ticket_number_existence(ticket_number: String) : String?
    {
        var name : String? = null
        runBlocking {
            withContext(Dispatchers.IO) {
                val url = "https://irbis.ugrasu.ru/ISAPI/irbis64r_plus/cgiirbis_64_ft.exe?IS_FIRST_AUTH=false&C21COM=F&I21DBN=AUTHOR&P21DBN=FOND&Z21FLAGID=1&Z21ID=$ticket_number&Z21FAMILY=&x=39&y=11"
                val document = Jsoup.connect(url).get()
                try
                {
                    val nameElement = document.getElementById("ctrl_changePassword_Button")?.parent()
                    name = nameElement?.text()?.split(' ')?.take(3)?.joinToString(" ")
                }
                catch (_: Exception)
                {
                }
            }
        }
        return name
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isLoggedIn())
        {
            val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("ticketNumber", sharedPreferences.getString("ticketNumber", ""))
            intent.putExtra("name", sharedPreferences.getString("name", ""))
            startActivity(intent)
            finish()
        }
        else
        {
            val loginButton: Button = findViewById(R.id.login_button)
            val ticketNumberEditText: EditText = findViewById(R.id.ticket_number_edittext)

            loginButton.setOnClickListener{
                val ticketNumber = ticketNumberEditText.text.toString().trim()
                val name = check_ticket_number_existence(ticketNumber)
                if (name != null)
                {
                    val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("ticketNumber", ticketNumber)
                    editor.putString("name", name)
                    editor.apply()

                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("ticketNumber", ticketNumber)
                    intent.putExtra("name", name)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    Toast.makeText(this, "Неверный номер читательского билета", Toast.LENGTH_SHORT).show()
                }
            }
        }

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
    }

    private fun isLoggedIn(): Boolean
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}