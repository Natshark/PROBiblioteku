package com.example.probiblioteku

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object
    {
        public const val DATABASE_VERSION = 1
        public const val DATABASE_NAME = "ReaderDatabase.db"
        public const val TABLE_NAME = "readers"
        public const val COLUMN_TICKET_NUMBER = "ticket_number"
    }

    override fun onCreate(db: SQLiteDatabase)
    {
        val createTableSQL = "CREATE TABLE $TABLE_NAME ($COLUMN_TICKET_NUMBER TEXT PRIMARY KEY)"
        db.execSQL(createTableSQL)

        insertTicketNumber(db, "12345")
        insertTicketNumber(db, "23456")
        insertTicketNumber(db, "34567")
        insertTicketNumber(db, "45678")
        insertTicketNumber(db, "56789")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun insertTicketNumber(db: SQLiteDatabase, ticketNumber: String)
    {
        val insertSQL = "INSERT INTO $TABLE_NAME ($COLUMN_TICKET_NUMBER) VALUES ('$ticketNumber')"
        db.execSQL(insertSQL)
    }
}