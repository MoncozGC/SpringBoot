# -*- coding:utf-8 -*-
# Author: MoncozGC
# Date  : 2022/12/12 20:41
# Desc  : 根据指定文件指定列导出数据到另外一个excel
import os
import sys
from datetime import datetime

import pandas as pd

nowDate = "_" + datetime.now().strftime('%Y%m%d%H%M%S')

# 指定文件名称
file_str = sys.argv[1]
# 指定列
choice_colum = sys.argv[2]


# 输入参数为excel表格所在目录及文件名
def to_files_excel2(root_dir, file):
    dfs = []
    files = list(file)
    result_file_str = ''
    # 遍历文件目录，将所有表格表示为pandas中的DataFrame对象
    # for root_dir, sub_dir, files in os.walk(r'' + dir):     # 第一个为起始路径，第二个为起始路径下的文件夹，第三个是起始路径下的文件。
    # for root_dir, sub_dir, files in os.walk(dir):  # 第一个为起始路径，第二个为起始路径下的文件夹，第三个是起始路径下的文件。
    for file in files:
        if file.endswith('xlsx'):
            # 构造绝对路径
            file_name = os.path.join(root_dir, file)
            # df = pd.read_excel(file_name)
            df_1 = list(pd.read_excel(file_name, nrows=1))  # 读取excel第一行数据并放进列表
            # excel第一行数据返回列表
            print(file_name)
            print(type(df_1))
            print(df_1)
            # 获取进入的文件名
            result_file_str = file.split('.')[0]

            # 读取文件内容  usecols=[1, 3, 4] 读取第1,3,4列
            df = pd.read_excel(file_name, usecols=choice_colum,
                               sheet_name='Sheet1', dtype=object)

            # pf = pd.read_excel('xxx.xls', usecols=[1, 3, 4], sheet_name='data')
            # print(pf)
            # 追加一列数据，将每个文件的名字追加进该文件的数据中，确定每条数据属于哪个文件
            excel_name = file.replace(".xlsx", "")  # 提取每个excel文件的名称，去掉.xlsx后缀
            df["文件名"] = excel_name  # 新建列名为“文件名”，列数据为excel文件名
            dfs.append(df)  # 将新建文件名列追加进汇总excel中
    # 行合并
    df_concat = pd.concat(dfs)
    # 构造输出目录的绝对路径
    out_path = os.path.join(root_dir, result_file_str + nowDate + '.xlsx')
    # 输出到excel表格中，并删除pandas默认的index列
    df_concat.to_excel(out_path, sheet_name='Sheet1', index=None)


if __name__ == '__main__':
    # 调用并执行函数
    to_files_excel2(r'D:\ERP', [file_str])
