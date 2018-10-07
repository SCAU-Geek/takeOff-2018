//index.js
Page({
  data: {
    currentTab: '-1',
    display: 'none',
    city: '',
    aqi: '',
    rainFallChance: '',
    temperature: '',
    feelingTmp: '',
    windDir: '',
    windSpd: '',
    humidity: '',
    weathercode: '999',
    backgroundUrl: '',
    screenHeight: '',
    livingIndex: [
      {inx: '0', type: '穿衣指数', brf: '', txt: '', src: '../../img/hanger.png'},
      {inx: '1', type: '感冒指数', brf: '', txt: '', src: '../../img/first-aid.png'},
      {inx: '2', type: '运动指数', brf: '', txt: '', src: '../../img/bike.png'},
      {inx: '3', type: '旅行指数', brf: '', txt: '', src: '../../img/package.png'},
      {inx: '4', type: '紫外线指数', brf: '', txt: '', src: '../../img/exposure.png'},
      {inx: '5', type: '洗车指数', brf: '', txt: '', src: '../../img/car.png'}
    ],
    backgroundImg: []
  },
  onLoad: function () {
    var that = this;
    //获取屏幕尺寸
    wx.getSystemInfo({
      success: function(res) {
        that.setData({
          screenHeight: res.windowHeight-2
        });
        // console.log(that.data.screenHeight);
      },
      fail: function() {
        console('fail: can not get screenHeight');
      }
    });
    var map = new Map();
    map.set('100', 'https://miao.su/images/2018/10/06/1006fc65.png');
    map.set('101', 'https://miao.su/images/2018/10/04/1015a485.png');
    map.set('102', 'https://miao.su/images/2018/10/04/1015a485.png');
    map.set('103', 'https://miao.su/images/2018/10/05/103fd1b5.png');
    map.set('104', 'https://miao.su/images/2018/10/05/104f1b15.png');
    map.set('201', 'https://miao.su/images/2018/10/05/104f1b15.png');
    map.set('200', 'https://miao.su/images/2018/10/05/2004f95c.png');
    map.set('202', 'https://miao.su/images/2018/10/05/202f0732.png');
    map.set('203', 'https://miao.su/images/2018/10/05/202f0732.png');
    map.set('204', 'https://miao.su/images/2018/10/05/202f0732.png');
    map.set('205', 'https://miao.su/images/2018/10/05/2052eeb8.png');
    map.set('206', 'https://miao.su/images/2018/10/05/2052eeb8.png');
    map.set('207', 'https://miao.su/images/2018/10/05/2052eeb8.png');
    map.set('208', 'https://miao.su/images/2018/10/05/2082d7a2.png');
    map.set('209', 'https://miao.su/images/2018/10/05/2082d7a2.png');
    map.set('210', 'https://miao.su/images/2018/10/05/2082d7a2.png');
    map.set('211', 'https://miao.su/images/2018/10/05/2082d7a2.png');
    map.set('212', 'https://miao.su/images/2018/10/05/2082d7a2.png');
    map.set('213', 'https://miao.su/images/2018/10/05/2082d7a2.png');
    map.set('300', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('301', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('302', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('303', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('304', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('307', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('308', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('310', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('311', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('312', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('313', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('316', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('317', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('318', 'https://miao.su/images/2018/10/05/3000f442.jpg');
    map.set('305', 'https://miao.su/images/2018/10/05/305bd7f1.png');
    map.set('309', 'https://miao.su/images/2018/10/05/305bd7f1.png');
    map.set('314', 'https://miao.su/images/2018/10/05/305bd7f1.png');
    map.set('399', 'https://miao.su/images/2018/10/05/305bd7f1.png');
    map.set('306', 'https://miao.su/images/2018/10/05/30689d26.jpg');
    map.set('315', 'https://miao.su/images/2018/10/05/30689d26.jpg');
    map.set('400', 'https://miao.su/images/2018/10/05/400f8ec6.jpg');
    map.set('408', 'https://miao.su/images/2018/10/05/400f8ec6.jpg');
    map.set('499', 'https://miao.su/images/2018/10/05/400f8ec6.jpg');
    map.set('401', 'https://miao.su/images/2018/10/05/401895a7.jpg');
    map.set('409', 'https://miao.su/images/2018/10/05/401895a7.jpg');
    map.set('402', 'https://miao.su/images/2018/10/05/402b9264.png');
    map.set('403', 'https://miao.su/images/2018/10/05/402b9264.png');
    map.set('404', 'https://miao.su/images/2018/10/05/402b9264.png');
    map.set('405', 'https://miao.su/images/2018/10/05/402b9264.png');
    map.set('406', 'https://miao.su/images/2018/10/05/402b9264.png');
    map.set('407', 'https://miao.su/images/2018/10/05/402b9264.png');
    map.set('410', 'https://miao.su/images/2018/10/05/402b9264.png');
    map.set('500', 'https://miao.su/images/2018/10/05/500a08cd.png');
    map.set('501', 'https://miao.su/images/2018/10/05/500a08cd.png');
    map.set('502', 'https://miao.su/images/2018/10/05/500a08cd.png');
    map.set('503', 'https://miao.su/images/2018/10/05/500a08cd.png');
    map.set('504', 'https://miao.su/images/2018/10/05/500a08cd.png');
    map.set('507', 'https://miao.su/images/2018/10/05/50776075.png');
    map.set('508', 'https://miao.su/images/2018/10/05/50776075.png');
    map.set('509', 'https://miao.su/images/2018/10/05/50776075.png');
    map.set('510', 'https://miao.su/images/2018/10/05/50776075.png');
    map.set('511', 'https://miao.su/images/2018/10/05/50776075.png');
    map.set('512', 'https://miao.su/images/2018/10/05/50776075.png');
    map.set('513', 'https://miao.su/images/2018/10/05/50776075.png');
    map.set('514', 'https://miao.su/images/2018/10/05/50776075.png');
    map.set('515', 'https://miao.su/images/2018/10/05/50776075.png');
    map.set('900', 'https://miao.su/images/2018/10/05/99950db5.png');
    map.set('901', 'https://miao.su/images/2018/10/05/99950db5.png');
    map.set('999', 'https://miao.su/images/2018/10/05/99950db5.png');
    that.setData({
      backgroundImg: map
    });
    that.getLocation();
  },
  //获取位置信息
  getLocation: function () {
    var that = this;
    wx.getLocation({
      success: function (res) {
        var latitude = res.latitude;
        var longitude = res.longitude;
        // console.log(latitude + ' ' + longitude);
        var locationUrl = 'https://search.heweather.com/find?location=' + longitude + ',' + latitude + '&key=40d7ac34554746f087c07f4051d00106';
        wx.request({
          url: locationUrl,
          success: function (res) {
            if(res.data.HeWeather6[0].status != 'ok') {
              console.log('fail ' + res.data.HeWeather6[0].status);
              that.failMessage();
            }
            else {
              // console.log(res);
              that.setData({
                city: res.data.HeWeather6[0].basic[0].parent_city
              });
              that.getWeather();
              that.getAir();
            }
          }
        });
      },
      fail: function () {
        console.log('fail');
        that.failMessage();
      },
      complete: function () {
        wx.showToast({
          icon: 'none',
          title: '刷新成功!',
        })
      }
    })
  },
  //获取天气信息
  getWeather: function () {
    var that = this;
    var weatherUrl = 'https://free-api.heweather.com/s6/weather?location=' + this.data.city + '&key=40d7ac34554746f087c07f4051d00106';
    wx.request({
      url: weatherUrl,
      success: function (res) {
        if (res.data.HeWeather6[0].status != 'ok') {
          console.log('fail: ' + res.data.HeWeather6[0].status);
          failMessage();
        }
        else {
          // console.log(res);
          var info = res.data.HeWeather6[0];
          var livingIndex = that.data.livingIndex;
          for(var i = 1; i <= 6; i++) {
            livingIndex[i-1].brf = info.lifestyle[i].brf;
            livingIndex[i-1].txt = info.lifestyle[i].txt;
          }
          that.setData({
            temperature: info.now.tmp,
            feelingTmp: info.now.fl,
            rainFallChance: info.daily_forecast[0].pop,
            windDir: info.now.wind_dir,
            windSpd: info.now.wind_spd,
            humidity: info.now.hum,
            weathercode: info.now.cond_code,
            weatherInfo: info.daily_forecast,
            livingIndex: livingIndex,
            backgroundUrl: that.data.backgroundImg.get(info.now.cond_code)
          });
        }
      },
      fail: function (res) {
        console.log('fail');
        that.failMessage();
      }
    });
  },
  //获取空气质量信息
  getAir: function (cid) {
    var that = this;
    var airUrl = 'https://free-api.heweather.com/s6/air/now?location=' + this.data.city + '&key=40d7ac34554746f087c07f4051d00106';
    wx.request({
      url: airUrl,
      success: function (res) {
        if (res.data.HeWeather6[0].status != 'ok') {
          console.log('fail: ' + res.data.HeWeather6[0].status);
          that.failMessage();
        }
        else {
          // console.log(res);
          that.setData({
            aqi: res.data.HeWeather6[0].air_now_city.aqi + ' ' + res.data.HeWeather6[0].air_now_city.qlty
          });
        }
      },
      fail: function (res) {
        console.log('fail');
        this.failMessage();
      }
    });
  },
  //错误提示
  failMessage: function () {
    wx.showModal({
      content: 'Oops! Something went wrong :(',
      showCancel: false
    });
  },
  //下拉刷新
  onPullDownRefresh : function () {
    this.getLocation();
  },
  //点击切换
  clickTab: function (x) {
    if(this.data.currentTab == '-1') {
      this.setData({
        currentTab: x.target.dataset.current,
        display: 'block'
      });
    }
    else if(this.data.currentTab == x.target.dataset.current) {
      this.setData({
        currentTab: '-1',
        display: 'none'
      });
    }
    else {
      this.setData({
        currentTab: x.target.dataset.current
      });
    }
  },
  //滑动切换
  swipeTab: function (x) {
    // console.log(x);
    this.setData({
      currentTab: x.detail.current
    });
  },
  onShareAppMessage: function () {
    return {
      title: 'Weather Now - 知你冷暖',
    };
  }
})