#!/bin/bash
java -Djava.rmi.server.hostname=$1 server.Server
