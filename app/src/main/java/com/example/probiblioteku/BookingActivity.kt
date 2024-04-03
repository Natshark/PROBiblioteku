package com.example.probiblioteku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.webkit.WebSettings;
import android.webkit.WebView;

import android.widget.EditText;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.Calendar;
import java.util.Locale


class BookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // navbar
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

        // Подгружаемый календарь яндекса
        val webView: WebView = findViewById(R.id.webViewCalendar)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadUrl("https://calendar.yandex.ru/embed/week?&layer_ids=28482469&tz_id=Asia/Yekaterinburg&layer_names=Бронирование")

        // корректировка длины вводимых значений
        val editText = findViewById<EditText>(R.id.name_surname_patronymic_edittext)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length ?: 0 > 37) {
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10F)
                }
                else if (s?.length ?: 0 > 30) {
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
                } else {
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })



        // динамичный выбор даты и времени

        // дата
        val calendar = Calendar.getInstance()
        val dateEditText: EditText = findViewById(R.id.date_edittext)
        dateEditText.keyListener = null
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            R.style.AppTheme_DialogTheme,
            DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val selectedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.time)
                dateEditText.setText(selectedDate)
                dateEditText.clearFocus()
            }, year, month, dayOfMonth)

        dateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                datePickerDialog.show()
            }
        }
        datePickerDialog.setOnCancelListener {
            dateEditText.clearFocus()
        }

        // время с
        val timeEditText: EditText = findViewById(R.id.time_edittext)
        timeEditText.keyListener = null
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this,
            R.style.AppTheme_DialogTheme,
            TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay: Int, minute: Int ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                timeEditText.setText(selectedTime)
                timeEditText.clearFocus()
            }, hourOfDay, minute, true)

        timeEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                timePickerDialog.show()
            }
        }
        timePickerDialog.setOnCancelListener {
            timeEditText.clearFocus()
        }

        // время до
        val timeEditText1: EditText = findViewById(R.id.time_edittext1)
        timeEditText1.keyListener = null
        val hourOfDay1 = calendar.get(Calendar.HOUR_OF_DAY)
        val minute1 = calendar.get(Calendar.MINUTE)

        val timePickerDialog1 = TimePickerDialog(this,
            R.style.AppTheme_DialogTheme,
            TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay: Int, minute: Int ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                timeEditText1.setText(selectedTime)
                timeEditText1.clearFocus()
            }, hourOfDay1, minute1, true)

        timeEditText1.setOnFocusChangeListener { _, hasFocus1 ->
            if (hasFocus1) {
                timePickerDialog1.show()
            }
        }
        timePickerDialog1.setOnCancelListener {
            timeEditText1.clearFocus()
        }

    }
}