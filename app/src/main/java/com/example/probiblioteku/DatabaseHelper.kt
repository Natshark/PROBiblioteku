package com.example.probiblioteku

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ReaderDatabase.db"
        const val TABLE_NAME = "readers"
        const val COLUMN_ID = "id"
        const val COLUMN_TICKET_NUMBER = "ticket_number"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_MIDDLE_NAME = "middle_name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (" +
                        "$COLUMN_ID INTEGER PRIMARY KEY," +
                        "$COLUMN_TICKET_NUMBER TEXT," +
                        "$COLUMN_FIRST_NAME TEXT," +
                        "$COLUMN_LAST_NAME TEXT," +
                        "$COLUMN_MIDDLE_NAME TEXT)")

        insertTicketNumber(db, "12345", "Иван", "Иванов", "Иванович")
        insertTicketNumber(db, "55555", "Александр", "Коваль", "Ринатович")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun insertTicketNumber(db: SQLiteDatabase, ticketNumber: String, firstName: String, lastName: String, middleName: String) {
        val insertSQL = "INSERT INTO $TABLE_NAME ($COLUMN_TICKET_NUMBER, $COLUMN_FIRST_NAME, $COLUMN_LAST_NAME, $COLUMN_MIDDLE_NAME) " +
                "VALUES ('$ticketNumber', '$firstName', '$lastName', '$middleName')"
        db.execSQL(insertSQL)
    }
}