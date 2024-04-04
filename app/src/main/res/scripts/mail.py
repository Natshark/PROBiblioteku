# import smtplib
# from email.mime.multipart import MIMEMultipart
# from email.mime.base import MIMEBase
# from email.mime.text import MIMEText
# # Создание объекта MIMEMultipart
# msg = MIMEMultipart()
#
# # Настройка заголовков письма
# msg['Subject'] = 'Новая заявка на бронирование!'
# msg['From'] = 'mobilelibraryugrasu@mail.ru'
# msg['To'] = 'danyakovalugrasu@gmail.com'
#
# # Создание текста сообщения
# text = 'бронирование 04.04....................................'
# msg.attach(MIMEText(text))
#
# # Отправка письма
# with smtplib.SMTP('smtp.mail.ru', 587) as server:
#     server.starttls()
#     server.login('mobilelibraryugrasu@mail.ru', 'JMP2f7xid8QzFK3FNDhY')
#     server.sendmail('mobilelibraryugrasu@mail.ru', 'danyakovalugrasu@gmail.com', msg.as_string())
print("Hello World!")
