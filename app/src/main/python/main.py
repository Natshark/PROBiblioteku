import irbis
from datetime import datetime

HOST = "192.168.0.99"  # "library1"
# HOST = "192.168.6.30"
PORT = 6666
LOGIN = '1'
PASS = '1'
DATABASE = 'RDR'
ARM = 'B'


class IRBIS:
    def __init__(self):
        self.connect()

    def connect(self):
        # Connect to the server
        self.client = irbis.Connection()
        self.client.parse_connection_string(
            f'host={HOST};port={PORT};database={DATABASE};user={LOGIN};password={PASS};arm={ARM};')
        self.ini = self.client.connect()

    def check_connection(self):
        clients = [cli.client_id for cli in self.client.get_server_stat().running_clients]
        if not str(self.client.client_id) in clients:
            return False
        else:
            return True

    def find_reader(self, ticket_number):
        if not self.check_connection():
            self.connect()
        found = self.client.search_all(f'"K={ticket_number}$"')
        return found

    def get_reader_name(self, reader_mfn):
        if not self.check_connection():
            self.connect()
        reader_record = self.client.read_record(reader_mfn)

        if reader_record:
            surname = str(reader_record.fields[2])[3:]
            name = str(reader_record.fields[3])[3:]
            patronymic = str(reader_record.fields[4])[3:]
            fio = surname + " " + name + " " + patronymic
            return fio

    @staticmethod
    def is_debtor(return_date):
        return return_date < datetime.now().replace(hour=0, minute=0, second=0, microsecond=0)

    def get_borrowed_books(self, reader_mfn):
        if not self.check_connection():
            self.connect()
        record = self.client.read_record(reader_mfn)

        borrowed_books = []
        for field in record.all(40):
            if field.have_subfield('a'):  # только записи с литературой, без посещений
                if field.first('f').value == '******':  # только еще не сданная литература
                    return_date = field.first_value('e')
                    title = field.first_value('c')

                    return_date_datetime = datetime.strptime(return_date, '%Y%m%d')
                    return_date_fmt = return_date_datetime.strftime('%d.%m.%Y')

                    borrowed_books.append([title, return_date_fmt, self.is_debtor(return_date_datetime)])

        return borrowed_books


def get_info(ticket_number):
    irbis_instance = IRBIS()
    irbis_instance.connect()
    if not irbis_instance.check_connection():
        print("Нет соединения!")
    else:
        reader = irbis_instance.find_reader(ticket_number)
        if reader:
            reader_mfn = reader[0]
            print(irbis_instance.get_reader_name(reader_mfn))
            for i in irbis_instance.get_borrowed_books(reader_mfn):
                print(i[0])
                print(i[1])
                print(i[2])
                print("--------------------------------------------------------------------------")
        else:
            print("Такого читательского билета нет")

    #01356131 - Саня
    #12720499 - Слава
    #002020 - Наталья Сергеевна
#get_info("12720499")
