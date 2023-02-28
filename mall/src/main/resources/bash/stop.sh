#!/bin/bash
PID=$(ps -ef | grep mall-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
        echo "[stop] Application not executed"
else
        kill -9 $PID
        PID=$(ps -ef | grep mall-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{ print $2 }')
        if [ -z "$PID" ]
                then
                        echo "[stop] Application stopped"
                else
                        echo "[stop] 应用程序无法停止"
        fi
fi