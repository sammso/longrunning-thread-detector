# Long running thread detector

## Description

This tool that allows you to analyse if the two consecutive thread dumps have threads that have a similar fingerprint.

## Compile project:
```.sh
mvn install
```

## To run:

```.sh
java -jar longrunning-detector-0.0.1-SNAPSHOT.jar <directory that contains thread dumps> <start with filter for thread name> <filter for stacktrace> <thread similary has to be 1 - 1000 (1000=equal)> <minumum length of thread stack>
```

Example Liferay:
```.sh
java -jar longrunning-detector-0.0.1-SNAPSHOT.jar . ajp-executor-threads com.liferay.portal.kernel.servlet.filters.invoker 750 30
``` 