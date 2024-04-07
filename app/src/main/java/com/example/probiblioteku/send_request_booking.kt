package com.example.probiblioteku

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
fun send_email(message_text_submit: String, subject: String, to: String) {
    val properties = Properties()
    properties["mail.smtp.host"] = "smtp.mail.ru"
    properties["mail.smtp.port"] = "587"
    properties["mail.smtp.auth"] = "true"
    properties["mail.smtp.starttls.enable"] = "true"

    val session = Session.getInstance(properties, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication("mobilelibraryugrasu@mail.ru", "JMP2f7xid8QzFK3FNDhY")
        }
    })

    try {
        val message = MimeMessage(session)
        message.setFrom(InternetAddress("mobilelibraryugrasu@mail.ru"))
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
        message.subject = subject.toString()
        message.setText(message_text_submit)
        Transport.send(message)
    }
    catch (e: MessagingException) {
        println("Failed to send email. Error: ${e.message}")
    }
}

fun sendEmailInBackground(messageTextSubmit: String, subject: String, to: String) {
    GlobalScope.launch(Dispatchers.IO) {
        send_email(messageTextSubmit, subject, to)
    }
}