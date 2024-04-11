from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options


def check_ticket_number_existence(ticket_number):
    # chrome_options = Options()
    # chrome_options.add_argument("--headless")
    # chrome_options.add_experimental_option('androidPackage', 'com.android.chrome')
    # browser = webdriver.Chrome('./chromedriver', options=chrome_options)
    # browser.get("https://irbis.ugrasu.ru/ISAPI/irbis64r_plus/cgiirbis_64_ft.exe?C21COM=F&I21DBN=FOND_FULLTEXT&P21DBN=FOND&Z21ID=&S21CNR=5")

    # ticket_number_input = browser.find_element(By.NAME, "Z21ID")
    # login_button = browser.find_element(By.XPATH, '/html/body/div[3]/table/tbody/tr[1]/td/div[4]/table/tbody/tr/td[6]/input')
    # ticket_number_input.send_keys(ticket_number)
    # login_button.click()
    # try:
    #     name = browser.find_element(By.XPATH, '//*[@id="ParamAuthor"]/fieldset/div/table/tbody/tr/td[1]').text.split('\n')
    #     browser.quit()
    #     return True
    # except:
    #     browser.quit()
    #     return False

def get_info():
    ticket_number = "01356131"
    print(check_ticket_number_existence(ticket_number))
