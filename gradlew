#!/usr/bin/env sh

##############################################################################
# Gradle start up script for UN*X
##############################################################################

APP_HOME=$(cd "$(dirname "$0")" && pwd)

# Resolve symlinks
while [ -h "$0" ] ; do
  ls=$(ls -ld "$0")
  link=$(expr "$ls" : '.*-> \(.*\)$')
  if expr "$link" : '/.*' > /dev/null; then
    0="$link"
  else
    0="$(dirname "$0")/$link"
  fi
done

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine Java
if [ -n "$JAVA_HOME" ] ; then
    JAVA_EXE="$JAVA_HOME/bin/java"
else
    JAVA_EXE=java
fi

# Default JVM options
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

exec "$JAVA_EXE" $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
