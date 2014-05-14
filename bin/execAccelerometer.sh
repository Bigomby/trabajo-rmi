#!/bin/bash

java -Djava.rmi.server.hostname=$1 -Djava.library.path=/usr/lib/jni -cp /usr/share/java/RXTXcomm.jar:. devices.AccelerometerImpl $1 $2

