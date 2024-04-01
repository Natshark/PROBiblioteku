package com.example.probiblioteku

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences


class MainActivity : AppCompatActivity()
{
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isLoggedIn())
        {
            val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("ticketNumber", sharedPreferences.getString("ticketNumber", ""))
            startActivity(intent)
            finish()
        }
        else
        {
            db = DatabaseHelper(this).writableDatabase

            val loginButton: Button = findViewById(R.id.login_button)
            val ticketNumberEditText: EditText = findViewById(R.id.ticket_number_edittext)

            loginButton.setOnClickListener{
                val ticketNumber = ticketNumberEditText.text.toString().trim()
                if (checkTicketNumberExists(ticketNumber))
                {
                    val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("ticketNumber", ticketNumber)
                    editor.apply()

                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("ticketNumber", ticketNumber)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    Toast.makeText(this, "Неверный номер читательского билета", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isLoggedIn(): Boolean
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun checkTicketNumberExists(ticketNumber: String): Boolean
    {
        val query = "SELECT * FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COLUMN_TICKET_NUMBER} = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(ticketNumber))
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }
}