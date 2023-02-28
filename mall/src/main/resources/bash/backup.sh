#!/bin/bash
cur_date="`date +%Y%m%d%H%M`"
JAR_NAME=mall-0.0.1-SNAPSHOT.jar

if [ -z $JAR_NAME ]
then
        read -p "输入需执行的JAR包名称: " JAR_NAME
fi
echo "[backup] 执行的JAR包名称为: " $JAR_NAME

cp -f ./$JAR_NAME ./backup/${JAR_NAME}_${cur_date}
echo [backup] $cur_date JAR包文件备份成功