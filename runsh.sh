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

### the jar to be agented ###
agentJar="${PWD}/java-agent-demo-1.0.jar"

### parameters in agentmain ###
agentMainParam="agentClass=com.kuyun.test.Target,agentMethod=f"

### startup main method ###
mainClass="com.kuyun.attach.Main"

java -cp ${cpath} -Dagent.Jar="${agentJar}=${agentMainParam}" ${mainClass} $1
