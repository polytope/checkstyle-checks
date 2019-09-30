./gradlew clean build jar && \
java -classpath \
  build/libs/checkstyle-checks-1.0-SNAPSHOT.jar:checkstyle-8.25-all.jar \
  com.puppycrawl.tools.checkstyle.Main -c config.xml -d \
  Test.java
