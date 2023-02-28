#!/bin/bash
JAR_NAME=$1
if [ -z $JAR_NAME ]
then
        read -p "输入需执行的JAR包名称: " JAR_NAME
fi
echo "[start] 执行的JAR包名称为: " $JAR_NAME

nohup java -jar $JAR_NAME &>./out.log &
echo "[start] 服务部署成功"