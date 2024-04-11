package com.example.probiblioteku

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.probiblioteku.BookDatabaseHelper.Companion.TABLE_NAME2
import android.widget.ImageView
import org.jsoup.Jsoup

class ProfileActivity : AppCompatActivity()
{

    @SuppressLint("Range", "SetTextI18n", "ClickableViewAccessibility", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val intent = intent
        val ticketNumber = intent.getStringExtra("ticketNumber")
        val nameTextView: TextView = findViewById(R.id.nameTextView)
        nameTextView.text = intent.getStringExtra("name")

        var pageContent: String? = null
        val irbisWebView : WebView = findViewById(R.id.irbisWebView)
        irbisWebView.setVerticalScrollBarEnabled(false);
        irbisWebView.setHorizontalScrollBarEnabled(false);
        irbisWebView.settings.javaScriptEnabled = true
        irbisWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val jsScript = """
                (function() {
                    var xpathsToHide = [
                        "/html/body/div[5]/div[1]/span",
                        "/html/body/div[5]/div[1]",
                        "/html/body/div[5]/div[2]/table/tbody/tr/td/span/a/img",
                        "/html/body/div[5]/div[2]/div[1]",
                        "/html/body/div[5]/div[2]/div[1]/div",
                        "/html/body/div[5]/div[2]/div[1]/div/p",
                        "/html/body/div[5]/div[2]/div[1]/div/img"
                    ];
                    var xpathsToKeep = [
                        "/html/body/div[3]/table/tbody/tr[1]/td/table/tbody/tr/td[2]/div/fieldset/div/div[1]/span",
                        "/html/body/div[3]/table/tbody/tr[1]/td/table/tbody/tr/td[2]/div/fieldset/div/div[1]",
                        "/html/body/div[3]/table/tbody/tr[1]/td/table/tbody/tr/td[2]/div/fieldset/div",
                        "/html/body/div[3]/table/tbody/tr[1]/td/table/tbody/tr/td[2]/div/fieldset",
                        "/html/body/div[3]/table/tbody/tr[1]/td/table/tbody/tr/td[2]/div",
                        "/html/body/div[3]/table/tbody/tr[1]/td/table/tbody/tr/td[2]",
                        "/html/body/div[3]/table/tbody/tr[1]/td/table/tbody/tr",
                        "/html/body/div[3]/table/tbody/tr[1]/td/table/tbody",
                        "/html/body/div[3]/table/tbody/tr[1]/td/table",
                        "/html/body/div[3]/table/tbody/tr[1]/td",
                        "/html/body/div[3]/table/tbody/tr[1]",
                        "/html/body/div[3]/table/tbody",
                        "/html/body/div[3]/table",
                        "/html/body/div[3]",
                        "/html/body",
                        "/html",
                        "/"
                    ];
            
                    // Найти все элементы на странице
                    var allElements = document.evaluate(
                        "//*",
                        document,
                        null,
                        XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE,
                        null
                    );
            
                    // Скрыть все элементы на странице
                    for (var i = 0; i < allElements.snapshotLength; i++) {
                        var element = allElements.snapshotItem(i);
                        element.style.display = 'none';
                    }
            
                    // Отобразить элементы, соответствующие XPath-выражениям
                    for (var j = 0; j < xpathsToKeep.length; j++) {
                        var xpathResult = document.evaluate(
                            xpathsToKeep[j],
                            document,
                            null,
                            XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,
                            null
                        );
            
                        for (var k = 0; k < xpathResult.snapshotLength; k++) {
                            var elementToShow = xpathResult.snapshotItem(k);
                            elementToShow.style.display = 'block';
                            elementToShow.style.color = "#000";
                            elementToShow.style.border = "none";
                            elementToShow.style.display = "flex";
                            elementToShow.style.justifyContent = "center";
                            elementToShow.style.fontFamily = "'Inter', sans-serif";
                            elementToShow.style.textAlign = "center";
                            if (k === 0 && j === 1) {
                                // Применяем стили к элементу с индексом 1
                                elementToShow.style.backgroundColor = "#5DB075";
                                elementToShow.style.width = "100%";
                                elementToShow.style.height = "50px";
                                elementToShow.style.borderRadius = "20px";
                                elementToShow.style.alignItems = "center";
                                elementToShow.style.fontSize = "15px";
                                elementToShow.style.fontWeight = "bold";
                                elementToShow.style.cursor = "pointer";
            
                                elementToShow.addEventListener('click', function() {
                                    for (var i = 0; i < xpathsToHide.length; i++) {
                                        var el = document.evaluate(
                                            xpathsToHide[i],
                                            document,
                                            null,
                                            XPathResult.FIRST_ORDERED_NODE_TYPE,
                                            null
                                        ).singleNodeValue;
                                        if (el) {
                                            el.style.display = 'none';
                                        }
                                    }
                                    var mainDiv = document.evaluate(
                                            "/html/body/div[5]",
                                            document,
                                            null,
                                            XPathResult.FIRST_ORDERED_NODE_TYPE,
                                            null
                                        ).singleNodeValue;
                                    mainDiv.style.left = '0px';
                                    mainDiv.style.right = '0px';
                                    mainDiv.style.bottom = '0px';
                                    mainDiv.style.border = '0px';
                                    mainDiv.style.width = '100%';
                                    mainDiv.style.height = '100%';
                                    
                                    var divWithText = document.evaluate(
                                            "/html/body/div[5]/div[2]",
                                            document,
                                            null,
                                            XPathResult.FIRST_ORDERED_NODE_TYPE,
                                            null
                                        ).singleNodeValue;
                                    divWithText.style.bottom = '0px';
                                    divWithText.style.border = '0px';
                                    divWithText.style.height = '100%';
                                });
                            }
                        }
                    }
                })();
            """.trimIndent()
                view?.evaluateJavascript(jsScript, null)
            }
        }

        irbisWebView.loadUrl("https://irbis.ugrasu.ru/ISAPI/irbis64r_plus/cgiirbis_64_ft.exe?IS_FIRST_AUTH=false&C21COM=F&I21DBN=AUTHOR&P21DBN=FOND&Z21FLAGID=1&Z21ID=$ticketNumber&Z21FAMILY=&x=39&y=11")

        val logoutButton: Button = findViewById(R.id.logout_button)
        logoutButton.setOnClickListener()
        {
            val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", false)
            editor.putString("ticketNumber", "")
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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
}