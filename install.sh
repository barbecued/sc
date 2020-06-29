#!/bin/bash

#installer script

if ! [ $(id -u) = 0 ]; then
   echo "This script needs to be run with root privileges." >&2
   exit 1
fi

echo "installing sc tool to /usr/local/bin"

cp sc sc.py /usr/local/bin/
