# -*- coding:utf-8 -*-
# Author: Min
# Date  : 2023/2/16
# Desc  : 中文转换为拼音
from xpinyin import Pinyin


def chinese_conversion(values):
    """
    中文转换为汉字, 首字母大写
    :param values: 汉字
    :return: 输出拼音
    """
    values_con = Pinyin().get_pinyin(values).split('-')
    result = ''
    for i in range(0, len(values_con)):
        result = result + values_con[i].capitalize()
    return result


if __name__ == '__main__':
    chinese_conversion("长沙")
