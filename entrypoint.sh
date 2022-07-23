#!/bin/sh
a=0
sleep 10
/etc/init.d/apache2 restart
/etc/init.d/apache2 start
service apache2 start
service apache2 restart
service apache2 start
while [ $a -le 10 ]
do
   echo $a
done