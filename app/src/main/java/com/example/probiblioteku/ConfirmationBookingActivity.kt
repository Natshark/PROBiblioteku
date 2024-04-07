package com.example.probiblioteku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class ConfirmationBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation_booking)

        fun generateRandomSixDigitNumber(): Int {
            val min = 100000
            val max = 999999
            return Random.nextInt(min, max + 1)
        }
        CacheManager(this).saveData("confirmCode", generateRandomSixDigitNumber().toString())

        sendEmailInBackground(CacheManager(this).getData("confirmCode", "Error"), "Код подтверждения", CacheManager(this).getData("email", "Error"))
        val confirmButton: Button = findViewById(R.id.confirmBookingButton)
        confirmButton.setOnClickListener {
            val confirmCode: EditText = findViewById(R.id.confirmationBookingEditText)
            val confirmCode_text = confirmCode.text.toString().trim()
            if (confirmCode_text == CacheManager(this).getData("confirmCode", "Error")) {
                sendEmailInBackground(CacheManager(this).getData("message", "Error"), "Заявка на бронирование", "danyakovalugrasu@gmail.com")
                val intent = Intent(this, BookingActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Заявка отправлена.\nС вами свяжутся в ближайшее время", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "Неверный код. Повторите попытку", Toast.LENGTH_LONG).show()
            }
        }
    }
}