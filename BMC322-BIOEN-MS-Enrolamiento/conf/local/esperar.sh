#!/bin/bash

url=$1
shift
cmd="$@"

while :
do
RES=$(curl -m 5 -k -s -o /dev/null -w '%{http_code}\n' $url )

printf $RES

if [ $RES -eq 200 ];then
   printf "SUCCESS " ;
   break
fi
printf "."
sleep 2
done
printf "\n"


exec $cmd
