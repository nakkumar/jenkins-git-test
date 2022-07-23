#!/bin/bash

docker build -f docker.http -t httpd-website:v5.0 /home/arun/work/docker/jenkins-git-test/.

docker tag  httpd-website:v5.0 nakkumar/jenkins-website-test:v5.0

docker push nakkumar/jenkins-website-test:v5.0

sleep 30

docker-compose -f httpd.yaml up -d 


docker exec apache service apache2 start