package com.example.probiblioteku

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.probiblioteku.BookDatabaseHelper.Companion.TABLE_NAME2
import org.w3c.dom.Text
import android.widget.ImageView


class ProfileActivity : AppCompatActivity()
{
    @SuppressLint("Range", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val intent = intent
        val ticketNumber = intent.getStringExtra("ticketNumber")
        val nameTextView: TextView = findViewById(R.id.nameTextView)

        val db2 = DatabaseHelper(this).readableDatabase
        val query = "SELECT ${DatabaseHelper.COLUMN_FIRST_NAME}, " +
                "${DatabaseHelper.COLUMN_LAST_NAME}, " +
                "${DatabaseHelper.COLUMN_MIDDLE_NAME} " +
                "FROM ${DatabaseHelper.TABLE_NAME} " +
                "WHERE ${DatabaseHelper.COLUMN_TICKET_NUMBER} = ?"
        val cursor2: Cursor = db2.rawQuery(query, arrayOf(ticketNumber))
        if (cursor2.moveToFirst())
        {
            val firstName = cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.COLUMN_FIRST_NAME))
            val lastName = cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.COLUMN_LAST_NAME))
            val middleName = cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.COLUMN_MIDDLE_NAME))

            nameTextView.text = "$lastName $firstName $middleName"
        }
        cursor2.close()

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
        val bookListView: ListView = findViewById(R.id.bookListView)
        val db = BookDatabaseHelper(this).readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME2", null)

        val bookData = mutableListOf<Any>()
        while (cursor.moveToNext())
        {
            val bookName = cursor.getString(cursor.getColumnIndex("book_name"))
            val bookDate = cursor.getString(cursor.getColumnIndex("book_date"))

            bookData.add(listOf(bookName, bookDate))
        }
        cursor.close()

        val adapter = object : ArrayAdapter<Any>(
            this,
            R.layout.list_item,
            R.id.bookNameTextView,
            bookData
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                val bookNameTextView: TextView = view.findViewById(R.id.bookNameTextView)
                val dateTextView: TextView = view.findViewById(R.id.dateTextView)
                val extendButton: Button = view.findViewById(R.id.extendButton)

                val bookDataItem = getItem(position) as List<String>
                val bookName = bookDataItem[0]
                val bookDate = bookDataItem[1]

                bookNameTextView.text = bookName
                dateTextView.text = bookDate

                extendButton.setOnClickListener()
                {
                    Toast.makeText(this@ProfileActivity, "Книга $bookName продлена", Toast.LENGTH_SHORT).show()
                }

                return view
            }
        }

        // Установка адаптера для ListView
        bookListView.adapter = adapter

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