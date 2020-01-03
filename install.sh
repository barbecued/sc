#!/bin/bash

#installer script

if ! [ $(id -u) = 0 ]; then
   echo "This script needs to be run with root privileges." >&2
   exit 1
fi

echo "installing sc tool to /usr/local/bin"

for file in boot.mod updates.mod performance.mod sc sc.py
do cp $file /usr/local/bin/$file
done
