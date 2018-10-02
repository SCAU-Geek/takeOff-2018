import requests
import json
from bs4 import BeautifulSoup

def tomorrow_weather(area):
	s = requests.session()
	s.trust_env = False
	cookie = None

	header = {
		'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8',
		'Accept-Encoding': 'gzip, deflate',
		'Accept-Language': 'zh,en-US;q=0.9,en;q=0.8,zh-CN;q=0.7',
		'Connection': 'keep-alive',
		'Host': 'www.weather.com.cn',
		'Referer': 'http://www.weather.com.cn/weathern/101280101.shtml',
		'Upgrade-Insecure-Requests': '1',
		'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36'
	}

	predict_data = {}
	predict_data['weather'] = []
	predict_data['detail'] = []
	predict_data['live_index'] = {}

	area_data = {
		'城区': 101280101,
		'番禺': 101280102,
		'从化': 101280103,
		'增城': 101280104,
		'花都': 101280105,
		'荔湾': 101280106,
		'越秀': 101280107,
		'珠海': 101280108,
		'天河': 101280109,
		'白云': 101280110,
		'黄埔': 101280111,
		'南沙': 101280112
	}

	weather_url = 'http://www.weather.com.cn/weather/' + str(area_data[area]) + '.shtml'
	main_resp = s.get(url=weather_url, headers=header)
	main_resp_soul = BeautifulSoup(main_resp.content, 'lxml')

	detail_data = main_resp_soul.find('div', id='curve').next_sibling.next_sibling.text
	detail_data = detail_data.replace('\n', '').replace('var hour3data=', '')
	detail_data = eval(detail_data)['7d']
	for i in range(-2,0):
		temp = detail_data[0][i].split(',')
		del temp[1], temp[-1]
		predict_data['detail'].append(temp)
	for i in range(0,6):
		temp = detail_data[1][i].split(',')
		del temp[1], temp[-1]
		predict_data['detail'].append(temp)
	#print(predict_data['detail'])


	weather_data = main_resp_soul.find('li', class_='sky skyid lv1')
	predict_data['weather'].append(weather_data.find('p').text)
	predict_data['weather'].append(weather_data.find('p', class_='tem').find('i').text + ' - ' + weather_data.find('p', class_='tem').find('span').text + '℃')
	predict_data['weather'].append(weather_data.find('p', class_='win').find('i').text)
	#print(predict_data['weather'])

	live_index = main_resp_soul.find('div', id='livezs')
	live_index = live_index.find('div', class_='hide').find('ul').find_all('li')
	del live_index[1], live_index[1], live_index[2]

	name_list = ['uv', 'clothes', 'air']
	tag_list = ['span', 'em', 'p']

	for i,each in enumerate(live_index):
		predict_data['live_index'][name_list[i]] = []
		for j in range(0,3):
			predict_data['live_index'][name_list[i]].append(each.find(tag_list[j]).text)

	#print(predict_data['live_index'])
	
	return predict_data