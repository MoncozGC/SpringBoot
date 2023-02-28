#!/bin/bash
# 部署到服务器执行脚本
JAR_NAME=mall-0.0.1-SNAPSHOT.jar

./stop.sh
./start.sh $JAR_NAME