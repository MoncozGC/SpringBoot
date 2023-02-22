# -*- coding:utf-8 -*-
# Author: MoncozGC
# Date  : 2022/12/27
# Desc  : 天气数据爬取, 并且入库
# 导入相关的包
from datetime import datetime
from pin_yin import chinese_conversion

import pymysql
import requests
import bs4
from bs4 import BeautifulSoup
import warnings

warnings.filterwarnings('ignore')


# 定义获取页面内容的方法
def getHTMLText(url):
    try:
        r = requests.get(url, timeout=30, verify=False)
        # #如果状态不是200，就会引发HTTPError异常
        r.raise_for_status()
        # r.encoding = r.apparent_encoding
        return r.text
    except:
        return ""


def insert_database(city_code, city_name, week, weather_day, weather_situation, temperature_min, temperature_max, air_quality, wind_situation, sunrise, sunset,
                    create_date, create_time):
    conn = pymysql.connect(connect_timeout=5, write_timeout=5, host='localhost', port=3306, user='root',
                           password='hadoop',
                           database='dev', charset="utf8")
    # 建库语句: CREATE DATABASE test;
    # 建表语句
    # CREATE TABLE IF NOT EXISTS `weather`
    # (
    #     `id`                INT UNSIGNED AUTO_INCREMENT COMMENT 'id',
    #     `city_name`         INT COMMENT '天气城市',
    #     `weather_day`       VARCHAR(100) NOT NULL COMMENT '天气日期',
    #     `weather_situation` VARCHAR(40)  NOT NULL COMMENT '天气情况',
    #     `temperature`       VARCHAR(40) COMMENT '天气温度',
    #     `air_quality`       VARCHAR(40) COMMENT '空气质量',
    #     `wind_situation`    VARCHAR(40) COMMENT '风向情况',
    #      sunrise            VARCHAR(30)  NULL COMMENT '日出时间',
    #      sunset             VARCHAR(30)  NULL COMMENT '日落时间',
    #     create_time         datetime DEFAULT CURRENT_TIMESTAMP COMMENT '爬取时间',
    #     PRIMARY KEY (`id`)
    # ) ENGINE = InnoDB
    #   DEFAULT CHARSET = utf8;

    # 使用cursor()方法获取操作游标
    cursor = conn.cursor()

    info_sql = """INSERT INTO dev.weather (`city_code`,`city_name`, `week`,`weather_day`, `weather_situation`, `temperature_min`, `temperature_max`, `air_quality`, `wind_situation`,
    `sunrise`, `sunset` ,`create_date`, `create_time`)
    VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"""

    # 执行SQL
    cursor.execute(info_sql,
                   (city_code, city_name, week, weather_day, weather_situation, temperature_min, temperature_max,
                    air_quality, wind_situation, sunrise, sunset,
                    create_date, create_time))

    # 4. 操作成功提交事务
    conn.commit()
    # 关闭游标
    cursor.close()


def date_conversion(date):
    """
    时间转换: 今天 -> 星期二; 明天 -> 星期三
    """
    week_list = ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"]
    if date == '今天':
        return week_list[datetime.today().isoweekday() - 1]
    elif date == '明天':
        return week_list[datetime.today().isoweekday()]
    else:
        # 将 周 替换成 星期
        return date.replace('周', '星期').replace('星期日', '星期天')


def sunrise_info(sun_info):
    """
    获取日出日落信息
    :param values:
    :return:
    """
    values = sun_info[sun_info.index('日出'):]
    return values[:8].strip("日出："), values[8:].strip("日落：")


if __name__ == '__main__':
    # 获取时间
    today = datetime.today().date()
    create_time = datetime.today().strftime("%H:%M:%S")
    print(create_time)
#     crawling_city = ['101250203?湘乡']
    crawling_city = ['101250203?湘乡', '101250101?长沙', '101010100?北京', '101020100?上海', '101280601?深圳', '101310101?海南', '101040100?重庆', '101240901?萍乡', '101050101?哈尔滨']
    # url = 'https://tianqi.so.com/weather/'
    # 遍历城市编码获取数据
    for i in crawling_city:
        url = 'https://tianqi.so.com/weather/' + i
        city_code = i.split('?')[0]
        city_name = i.split('?')[1]
        # print(city_code + "-" + city_name)
        html = getHTMLText(url)
        soup = BeautifulSoup(html, 'html.parser')
        url = ''

        # 提取目标值
        content_list = soup.find_all('ul', class_='weather-columns')
        city_list = soup.find_all('strong', class_='change-title')
        city_info = soup.find_all('div', class_='cur-weather g-fl')

        # 获取当天的日出日落信息
        sunrise = ""
        sunset = ""
        for value in city_info:
            sunrise, sunset = sunrise_info(value.get_text())
            break

        city = ''
        for c in city_list:
            city = c.get_text()

        # 格式化输出
        # tplt = "{0:^8}\t{1:^8}\t{2:^8}\t{3:^8}\t{4:^8}\t{5:^8}\t{6:^8}\t{7:^8}"information_schema
        # print(tplt.format('城市', 'Week', '日期', '天气情况', '最低温度', '最高温度', '天气质量', '凤向情况'))
        insert_num = 0
        for c in content_list:
            tmp = c.get_text()
            split_str = date_conversion(tmp.strip().split()[0])
            week = split_str
            date_target = tmp.split()[1].strip("()")
            situation = tmp.strip().split()[2]
            temperature_min = tmp.strip().split()[3].split('℃')[0].split('/')[0] + '℃'
            temperature_max = tmp.strip().split()[3].split('℃')[0].split('/')[1] + '℃'
            air_quality = tmp.strip().split()[3].split('℃')[1][0]
            wind_situation = tmp.strip().split()[3].split('℃')[1][1:] + " " + tmp.strip().split()[4]
            # print(tplt.format(city, week, date_target, situation, temperature_min, temperature_max, air_quality, wind_situation))
            try:
                insert_database(city_code, city, week, date_target, situation, temperature_min, temperature_max, air_quality, wind_situation, sunrise, sunset,
                                today, create_time)
                insert_num = insert_num + 1
            except Exception as e:
                ""

        if insert_num > 0:
            # ljust右侧填充对齐字符串
            print(chinese_conversion(city_name).ljust(12) + "insert database num: " + str(insert_num))
        else:
            print(chinese_conversion(city_name).ljust(12) + "No Change...")
