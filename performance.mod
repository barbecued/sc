#!/bin/bash

function cpu_load() {
	SARDIR=sar
	if [ -d $SARDIR ]; then
		if ls $SARDIR/sar* 1> /dev/null 2>&1; then
			echo "Sar files exist"
		else
			echo "Sar files do not exist"
		fi
	else
		echo "Sar files do not exist"
	fi
}
