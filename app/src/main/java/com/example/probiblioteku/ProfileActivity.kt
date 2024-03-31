package com.example.probiblioteku

import android.annotation.SuppressLint
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

class ProfileActivity : AppCompatActivity()
{
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

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

        // Создание адаптера
        val adapter = object : ArrayAdapter<Any>(
            this,
            R.layout.list_item,
            R.id.bookNameTextView,
            bookData
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                // Получение ссылок на элементы макета list_item_book.xml
                val bookNameTextView: TextView = view.findViewById(R.id.bookNameTextView)
                val dateTextView: TextView = view.findViewById(R.id.dateTextView)
                val extendButton: Button = view.findViewById(R.id.extendButton)

                // Получение данных о книге и ее дате
                val bookDataItem = getItem(position) as List<String>
                val bookName = bookDataItem[0]
                val bookDate = bookDataItem[1]

                // Установка текста названия книги и даты
                bookNameTextView.text = bookName
                dateTextView.text = bookDate

                // Обработка нажатия кнопки "продлить"
                extendButton.setOnClickListener {
                    // Добавьте код для продления аренды книги
                    // например, показ диалогового окна или другие действия
                    Toast.makeText(this@ProfileActivity, "Книга $bookName продлена", Toast.LENGTH_SHORT).show()
                }

                return view
            }
        }

        // Установка адаптера для ListView
        bookListView.adapter = adapter
    }
}