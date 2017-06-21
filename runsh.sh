#!/usr/bin/env bash

rm -rf target
mvn clean
mvn package
cd target

RESOURCE_PATH=classes/

cpath=$CLASSPATH:${RESOURCE_PATH}

for file in lib/*.jar; do
    cpath=$cpath:$file
done

pid=`ps -ef|grep Target|grep -v grep|awk '{print $2}'`
agentJar="/Users/xuwuqiang/Documents/git/java-agent-demo/target/java-agent-demo-1.0.jar"
mainClass="com.kuyun.attach.Test"

java -cp ${cpath} -Dagent.Jar="${agentJar}" ${mainClass} ${pid}
