from flask import render_template, request
from app import app
import app.weather_predict as weather_predict

@app.route('/', methods=['GET', 'POST'])
@app.route('/<area>', methods=['GET', 'POST'])
@app.route('/index', methods=['GET', 'POST'])
@app.route('/index/<area>', methods=['GET', 'POST'])
def index(area='城区'):
	area_data = [['城区','番禺','从化','增城'],['花都','荔湾','越秀','珠海'],['天河','白云','黄埔','南沙']]
	predict_data = weather_predict.tomorrow_weather(area)
	#print(predict_data)
	return render_template('index.html', area_data=area_data, area=area, detail_data=predict_data['detail'], weather_data = predict_data['weather'], live_index = predict_data['live_index'])