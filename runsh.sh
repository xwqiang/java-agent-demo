#!/usr/bin/env bash

mvn clean
mvn package
cd target

RESOURCE_PATH=classes/


case "`uname`" in
  Darwin*)
      # In Mac OS X, tools.jar is classes.jar and is kept in a
      # different location. Check if we can locate classes.jar
      # based on ${JAVA_VERSION}
      TOOLS_JAR="/System/Library/Frameworks/JavaVM.framework/Versions/${JAVA_VERSION}/Classes/classes.jar"

      # if we can't find, try relative path from ${JAVA_HOME}. Usually,
      # /System/Library/Frameworks/JavaVM.framework/Versions/1.6.0/Home
      # is JAVA_HOME. (or whatever version beyond 1.6.0!)
      if [ ! -f ${TOOLS_JAR} ] ; then
          TOOLS_JAR="${JAVA_HOME}/../Classes/classes.jar"
      fi

      # If we still can't find, tell the user to set JAVA_VERSION.
      # This way, we can avoid zip file errors from the agent side
      # and "connection refused" message from client.
      if [ ! -f ${TOOLS_JAR} ] ; then
          echo "Please set JAVA_VERSION to the target java version"
#          exit 1
      fi
  ;;
  *)
      TOOLS_JAR="${JAVA_HOME}/lib/tools.jar"
  ;;
esac

## 我本地(maxOS)的配置
TOOLS_JAR=${JAVA_HOME}/lib/tools.jar

cpath=${RESOURCE_PATH}:${TOOLS_JAR}


### the jar to be agented ###
agentJar="${PWD}/java-agent-demo-1.0.jar"

### parameters in agentmain ###
agentMainParam="agentClass=com/kuyun/test/Target,agentMethod=f"

### startup main method ###
mainClass="com.kuyun.attach.Main"

java -cp ${cpath} -Dagent.Jar="${agentJar}=${agentMainParam}" ${mainClass} $1
