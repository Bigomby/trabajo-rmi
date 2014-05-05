#!/bin/bash

java -Djava.rmi.server.hostname=$1 -Djava.library.path=/usr/lib/jni -cp /usr/share/java/RXTXcomm.jar:. devices.AlarmImpl $2

