#! -*- coding : utf-8 -*-
from bs4 import BeautifulSoup
import datetime
import re
import requests

"""
    一个简单的爬虫类，使用 bs4 与 requests 模块编写
"""
class crawler(object):
    def get_weather(self):
        """
                获取第二天的天气并返回给主应用
                :param:{null}
                :return:{list} success：返回一个包含风向 天气 天气图片 最高温度 最低温度的数组
                               failed：返回失败信息
        """
        url = r'http://www.tianqi.com/guangzhou/'
        with requests.get(url) as f:
            data = f.text
            soup = BeautifulSoup(data, features='html.parser')
            weather_all = soup.select('.txt li')
            wind = weather_all[7:]
            weather = weather_all[:7]
            weather_img = soup.select('.week img')
            high_temper = soup.select('.zxt_shuju span')
            low_temper = soup.select('.zxt_shuju b')
            ISOTIMEFORMAT = '%H'
            time = int(datetime.datetime.now().strftime(ISOTIMEFORMAT))
            if time < 6 and time > 0:
                return [wind[0].string, weather[0].string, weather_img[0].get('src'), high_temper[0].string, low_temper[0].string, "现在已经是第二天了，阿伟已为你改为获取今天的天气"]  #一般凌晨都是想获取今天的天气情况
            elif time > 0:
                return [wind[1].string, weather[1].string, weather_img[1].get('src'), high_temper[1].string, low_temper[1].string, "天气获取成功"]
            else:
                return {"天气获取失败"}
    def get_index(self):
        """
        :param: {null}
        :return: {list} 一个包含各种生活指数的html代码的数组
        """
        url = r'http://www.tianqi.com/guangzhou/'
        with requests.get(url) as f:
            data = f.text
            soup = BeautifulSoup(data, features='html.parser')
            index_info = soup.select('.weather_life300 ul')
            return index_info

if __name__ == '__main__':
    crawl = crawler()
    data = crawl.get_weather()
    print('明日风向：', data[0])
    print('明日天气：', data[1])
    print('明日最高气温：', data[3], '°C')
    print('明日最低气温：', data[4], '°C')
    crawl.get_index()