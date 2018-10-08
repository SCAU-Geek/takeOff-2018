#! -*-coding : utf-8 -*-
import crawler
import datetime
from flask import Flask, render_template

app = Flask(__name__)


@app.route('/')
def hello_world():
    crawl = crawler.crawler()
    index_info = str(crawl.get_index())[1:-1]
    ISOTIMEFORMAT = '%Y/%M/%d'
    date = datetime.datetime.now().strftime(ISOTIMEFORMAT)
    data = crawl.get_weather()   #返回一个包含风向 天气 天气图片 最高温度 最低温度的数组
    return render_template('index.html',wind = data[0], weather = data[1], weather_img = data[2], \
                           high = data[3], low = data[4], msg = data[5],index_info = index_info, date = date)

if __name__ == '__main__':
    app.run()
