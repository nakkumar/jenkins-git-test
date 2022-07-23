#!/bin/bash

#docker build -f docker.http -t httpd-website:v5.0 /home/arun/work/docker/jenkins-git-test/.

docker build -f docker.http -t httpd-website:v6.0 $WORKSPACE/.

docker tag  httpd-website:v6.0 nakkumar/jenkins-website-test:v6.0

docker push nakkumar/jenkins-website-test:v6.0

sleep 30

docker-compose -f $WORKSPACE/httpd.yaml up -d 

sleep 30

docker exec apache service apache2 start
