package com.example.probiblioteku

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.util.TypedValue
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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

        // подгружаемый календарь яндекса
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
                } else if (s?.length ?: 0 > 30) {
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

                val selectedDate =
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.time)
                dateEditText.setText(selectedDate)
                dateEditText.clearFocus()
            }, year, month, dayOfMonth
        )

        dateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                datePickerDialog.show()
            }
        }
        datePickerDialog.setOnCancelListener {
            dateEditText.clearFocus()
        }

        // время с
        val timeEditText: EditText = findViewById(R.id.time_edittext) // time

        timeEditText.keyListener = null
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this,
            R.style.AppTheme_DialogTheme,
            TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay: Int, minute: Int ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                timeEditText.setText(selectedTime)
                timeEditText.clearFocus()
            }, hourOfDay, minute, true
        )

        timeEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                timePickerDialog.show()
            }
        }
        timePickerDialog.setOnCancelListener {
            timeEditText.clearFocus()
        }

        // время до
        val timeEditText1: EditText = findViewById(R.id.time_edittext1) // time

        timeEditText1.keyListener = null
        val hourOfDay1 = calendar.get(Calendar.HOUR_OF_DAY)
        val minute1 = calendar.get(Calendar.MINUTE)

        val timePickerDialog1 = TimePickerDialog(this,
            R.style.AppTheme_DialogTheme,
            TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay: Int, minute: Int ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                timeEditText1.setText(selectedTime)
                timeEditText1.clearFocus()
            }, hourOfDay1, minute1, true
        )

        timeEditText1.setOnFocusChangeListener { _, hasFocus1 ->
            if (hasFocus1) {
                timePickerDialog1.show()
            }
        }
        timePickerDialog1.setOnCancelListener {
            timeEditText1.clearFocus()
        }

        // формирование и отправка письма с проверками
        val fullname: EditText = findViewById(R.id.name_surname_patronymic_edittext)
        val numberphone: EditText = findViewById(R.id.number_phone_edittext)
        val email: EditText = findViewById(R.id.email_edittext)
        val description: EditText = findViewById(R.id.description_edittext)
        val submitButton: Button = findViewById(R.id.submit_button)
        // timeEditText - время С, timeEditText1 - время ДО, dateEditText - дата

        submitButton.setOnClickListener {
            val fullname_text = fullname.text.toString().trim()
            val numberphone_text = numberphone.text.toString().trim()
            val email_text = email.text.toString().trim()
            val description_text = description.text.toString().trim()
            val time_text = timeEditText.text.toString().trim()
            val time_text1 = timeEditText1.text.toString().trim()
            val date_text = dateEditText.text.toString().trim()

            fun countWords(editText: EditText): Int {
                val text = editText.text.toString().trim()
                val words = text.split("\\s+".toRegex()).filter { it.isNotEmpty() }
                return words.size
            }

            fun isValidEmail(email: String): Boolean {
                return Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }

            fun checkInputDate(inputDate: String): Boolean {
                val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date()).toString()

                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val currentDate_DateFormat = dateFormat.parse(currentDate)
                val inputDate_DateFormat = dateFormat.parse(inputDate)

                if (currentDate_DateFormat.before(inputDate_DateFormat)) {
                    return true
                }
                else if (currentDate_DateFormat.after(inputDate_DateFormat)) {
                    return false
                }
                else {
                    return true
                }
            }


            fun checkInputTime(time: String, time1: String): Boolean {
                val time_hh: Int = "${time[0]}${time[1]}".toInt()
                val time_mm: Int = "${time[3]}${time[4]}".toInt()
                val time_hh1: Int = "${time1[0]}${time1[1]}".toInt()
                val time_mm1: Int = "${time1[3]}${time1[4]}".toInt()

                if (time_hh < 8 || time_hh >= 18 || time_hh1 < 8 || time_hh1 >= 18) {
                    if (time_hh == 18) {
                        return false
                    }
                    else if (time_hh1 == 18 && time_mm1 == 0) {
                        return true
                    }
                    return false
                }
                else if (time_hh > time_hh1) {
                    return false
                }
                else if (time_hh == time_hh1) {
                    if (time_mm > time_mm1 || time_mm == time_mm1) {
                        return false
                    }
                    else {
                        return true
                    }
                }
                else {
                    return true
                }

            }

            fun replaceMonthsAndRemoveSpaces(input: String): String {
                val monthsMap = mapOf(
                    "января" to "01",
                    "февраля" to "02",
                    "марта" to "03",
                    "апреля" to "04",
                    "мая" to "05",
                    "июня" to "06",
                    "июля" to "07",
                    "августа" to "08",
                    "сентября" to "09",
                    "октября" to "10",
                    "ноября" to "11",
                    "декабря" to "12"
                )

                var result = input
                for ((month, number) in monthsMap) {
                    result = result.replace(month, number)
                }
                return result.replace(" ", "")
            }

            fun parseDateTime(date: String, time: String, time1: String): Boolean {
                val doc = Jsoup.connect("https://calendar.yandex.ru/export/html.xml?private_token=59ea5b32855e5e88227a40a5b6ea1298265bdea6&tz_id=Asia/Yekaterinburg&limit=90").get()
                val spans = doc.select("span") // выбираем все элементы span
                val spanTexts = mutableListOf<String>()
                for (span in spans) {
                    val replacedText = replaceMonthsAndRemoveSpaces(span.text())
                    spanTexts.add(replacedText)
                }
                val dateList = spanTexts.map { it.substring(0, it.length - 5) }.map { it.substring(0, 2) + "." + it.substring(2, 4) + "." + it.substring(4) }
                val timeList = spanTexts.map { it.substring(it.length - 5) }

                for (i in dateList.indices step 2) {
                    if (dateList[i] == date) {
                        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

                        val formattedStartTime = sdf.parse(timeList[i])
                        val formattedEndTime = sdf.parse(timeList[i+1])

                        val formattedCheckTime = sdf.parse(time)
                        val formattedCheckTime1 = sdf.parse(time1)

                        if (formattedCheckTime in formattedStartTime..formattedEndTime || formattedCheckTime1 in formattedStartTime..formattedEndTime) {
                            return false
                        }
                    }
                }
                return true

            }

            fun startParseDateTimeInBackground(date: String, time: String, time1: String): Boolean = runBlocking {
                val result = async(Dispatchers.IO) { parseDateTime(date, time, time1) }
                result.await()
            }

            if (fullname_text == "" || numberphone_text == "" || description_text == "" || time_text == "" || time_text1 == "" || date_text == "" || email_text == "") {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else if (countWords(fullname) < 2) {
                Toast.makeText(this, "Проверьте правильность ввода ФИО", Toast.LENGTH_SHORT).show()
            }
            else if (numberphone_text.length != 10 && numberphone_text.length != 11) {
                Toast.makeText(
                    this,
                    "Проверьте правильность ввода номера телефона",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (!isValidEmail(email_text)) {
                Toast.makeText(
                    this,
                    "Проверьте правильность ввода Email",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (!checkInputTime(time_text, time_text1) || !checkInputDate(date_text)) {
                Toast.makeText(
                    this,
                    "Проверьте правильность ввода даты и времени",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (!startParseDateTimeInBackground(date_text, time_text, time_text1)) {
                Toast.makeText(
                    this,
                    "Выбранная дата и время заняты",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                val messageText = "ФИО: " + fullname_text + "\n" + "Дата и время: " + date_text + " с " + time_text + " до " + time_text1 + "\n" + "Номер телефона: " + numberphone_text + "\n" + "Email: " + email_text + "\n" + "Пожелания(описание): " + description_text + "\n" + "Пользователь ожидает вашего звонка!"
                CacheManager(this).saveData("message", messageText)
                CacheManager(this).saveData("email", email_text)

                fullname.setText("")
                numberphone.setText("")
                description.setText("")
                timeEditText.setText("")
                timeEditText1.setText("")
                dateEditText.setText("")

                val intent = Intent(this, ConfirmationBookingActivity::class.java)
                startActivity(intent)
            }

        }

    }
}