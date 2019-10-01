#!/bin/bash
./gradlew clean build jar

FILE="build/libs/checkstyle-checks-*.jar"

java -classpath "$(echo $FILE)":checkstyle-8.25-all.jar \
  com.puppycrawl.tools.checkstyle.Main -c config.xml -d \
  ../auto/auto--ctrl/
  #Test.java
