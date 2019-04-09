#!/bin/sh

# use "exec" to propagate SIGTERM to a child process, so java will gracefully shutdown
exec java -jar gallery-0.1.0-SNAPSHOT.jar
