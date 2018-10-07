from urllib.request import urlopen
import os
import time
from html.parser import HTMLParser
import platform

base_url = 'http://www.nmc.cn/publish/forecast/AGD/guangzhou.html'

class ForecastParser(HTMLParser):
    state = -1

    weekday = None
    day_wdec = None
    night_wdec = None
    day_temp = None
    night_temp = None

    def handle_starttag(self, tag, attrs):
        if ('id', 'forecast') in attrs:
            self.state = 0

        if self.state < 0:
            return

        if tag == 'p' and ('class', 'dname') in attrs:
            self.state = 1
        
        if self.state == 2 and tag == 'p':
            self.state = 3
        elif self.state == 4 and ('class', 'wdesc') in attrs:
            self.state = 5
        elif self.state == 6 and ('class', 'temp') in attrs:
            self.state = 7
        elif self.state == 8:
            self.state = -1


    def handle_data(self, data):
        # State -1: not ready to parse
        # State 0: ready to parse
        # State 1: prepare to read dname
        # State 2: finish reading dname
        # State 3: prepare to read date
        # State 4: finish reading date
        # State 5: prepare to read weather description
        # State 6: finish reading weather description
        # State 7: prepare to read temperature
        # State 8: finish reading temperature
        if self.state == 1:
            data = data.replace(' ', '')
            if data == '明天':
                self.state = 2
            else:
                self.state = 0
        elif self.state == 3:
            self.weekday = data
            self.state = 4
        elif self.state == 5:
            if not self.day_wdec:
                self.day_wdec = data
                self.state = 4
            elif not self.night_wdec:
                self.night_wdec = data
                self.state = 6
        elif self.state == 7:
            if not self.day_temp:
                self.day_temp = data.replace(' ', '')
                self.state = 6
            elif not self.night_temp:
                self.night_temp = data.replace(' ', '')
                self.state = 8

    def description(self):
        return '广州市明天(%s)日间天气%s, 温度为%s; 夜间天气%s, 温度为%s。'%(
            self.weekday, self.day_wdec, self.day_temp, self.night_wdec, self.night_temp)

def retrieve_page():
    content = None
    with urlopen(base_url) as conn:
        content = conn.read().decode()
    return content

toaster = None

def send_osx_notification(title, message):
    '''
    None send_osx_notification(title -> str, message -> str)
    This function uses macOS built-in AppleScript interpreter to display
    a Notification Center message.
    '''
    os.system("osascript -e 'display notification \"%s\" with title \"%s\"'"%(message, title))

def send_window_notification(title, message):
    toaster.show_toast(title, message)

send_notification = None

if platform.system() == 'Windows':
    try:
        import win10toast
        toaster = win10toast.ToastNotifier()
        send_notification = send_window_notification
    except:
        print('Fail to import win10toaster!')
        print('Try this command to install the requirement:')
        print('> pip install win10toaster')
        exit()
elif platform.system() == 'Darwin':
    send_notification = send_osx_notification
else:
    print('This script supports only Windows 10 and macOS.')
    exit()

parser = ForecastParser()
while True:
    content = retrieve_page()
    parser.feed(content)
    send_notification('明日天气', parser.description())
    time.sleep(3600)
