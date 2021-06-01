#!/bin/bash

export userid=$(whoami)
if [ ${userid} == 'root' ]
then
  echo "root 권한으로 실행할 수 없습니다"
  exit 0
fi
export JAVA=java
export PORT=8080
export CONTEXTPATH=/
export T_NAME="soap-2021.1.war --server.port=${PORT} --server.servlet.contextPath=${CONTEXTPATH} -Xms32m -Xmx32m"
export T_USAGE="Usage: $0 {start | stop | restart | status | log}"

fn_pid() {
  echo `ps auxwww | grep "${T_NAME}" | grep -v grep | tr -s " "|cut -d" " -f2`
}

fn_start() {
  export pid=$(fn_pid)
  if [ ! -n "${pid}" ]
  then
    echo "Starting ${T_NAME}"
    ${JAVA} -jar ${T_NAME}
  fi
  fn_status
}

fn_stop() {
  export pid=$(fn_pid)
  if [ -n "${pid}" ]
  then
    echo "Stoping ${T_NAME}"
    for pid_item in ${pid}
    do
      kill -9 ${pid_item}
    done
  fi
  fn_status
}

fn_status() {
  export pid=$(fn_pid)
  if [ -n "${pid}" ]
    then echo "${T_NAME} is running with pid: ${pid}"
  else
    echo "${T_NAME} is not running"
  fi
}

fn_log() {
 tail -f logs/log.log
}

case $1 in
  start) fn_start ;;
  stop) fn_stop ;;
  restart)
      fn_stop
      sleep 3
      fn_start ;;
  status) fn_status ;;
  log) fn_log ;;
  *) echo ${T_USAGE} ;;
esac
exit 0
