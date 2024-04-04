package com.example.probiblioteku

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "BookDatabase.db", null, 1)
{
    companion object
    {
        public const val TABLE_NAME2 = "Books"
    }
    override fun onCreate(db: SQLiteDatabase)
    {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
        val createTableSQL = "CREATE TABLE $TABLE_NAME2 (book_name TEXT, book_date TEXT)"
        db.execSQL(createTableSQL)

        insertTicketNumber(db, "Математика", "29.07.2024")
        insertTicketNumber(db, "История", "14.06.2024")
        insertTicketNumber(db, "Основы програмирование: путь к успеху. ГЛАВНОЕ — НАЧАТЬ!!!)", "25.05.2024")
        insertTicketNumber(db, "Биология беспозвоночных. Первый том", "25.05.2024")
        insertTicketNumber(db, "История Семи королевств", "01.05.2024")
        insertTicketNumber(db, "Физкультура", "14.04.2024")
        insertTicketNumber(db, "Химия", "10.03.2024")
        insertTicketNumber(db, "Плотничество", "12.02.2024")
        insertTicketNumber(db, "Кузнеческое дело", "15.01.2024")
        insertTicketNumber(db, "Математика", "29.07.2024")
        insertTicketNumber(db, "История", "14.06.2024")
        insertTicketNumber(db, "Основы програмирование: путь к успеху. ГЛАВНОЕ — НАЧАТЬ!!!)", "25.05.2024")
        insertTicketNumber(db, "Биология беспозвоночных.Первый том", "25.05.2024")
        insertTicketNumber(db, "История Семи королевств", "01.05.2024")
        insertTicketNumber(db, "Физкультура", "14.04.2024")
        insertTicketNumber(db, "Химия", "10.03.2024")
        insertTicketNumber(db, "Плотничество", "12.02.2024")
        insertTicketNumber(db, "Кузнеческое дело", "15.01.2024")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
        onCreate(db)
    }

    private fun insertTicketNumber(db: SQLiteDatabase, bookName: String, bookDate: String)
    {
        val insertSQL = "INSERT INTO $TABLE_NAME2 ('book_name', 'book_date') VALUES ('$bookName', '$bookDate')"
        db.execSQL(insertSQL)
    }
}