## 明天天气

### 描述 ###

一个简单轻量的天气获取模块

一个利用这个模块构造的网页

### 快速上手

1. 使用 git 克隆此项目 `git clone -b Weilet git@github.com:SCAU-Geek/takeOff-2018.git `
2. 在安装 python3 的情况下，在命令行中使用`python crawler.py`查看明天天气
3. 或者访问已部署网站 <a>47.107.46.36</a> 获取更详细的资料

### 文件结构

crawler.py	爬虫类

app.py	主应用

template	模板文件文件夹

##|————	index.html 主要页面

static	静态文件文件夹

### 解决思路
通过 python 的 bs4 与 requests 编写爬虫
爬取后呈现在网页端
当然，你也可以直接用python运行该爬虫获取天气，
甚至你可以用 crontab 部署定时任务（linux下）

