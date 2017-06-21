#!/usr/bin/env bash

#mvn clean
#mvn package
cd target

RESOURCE_PATH=classes/

cpath=$CLASSPATH:${RESOURCE_PATH}

for file in lib/*.jar; do
    cpath=$cpath:$file
done

mainClass="com.kuyun.test.Target"

java -cp ${cpath}  ${mainClass}
