#!/bin/bash

function cpu_load() {
	printf "\n${bold}CHECKING SAR DATA${normal}\n"
	SARDIR=sar
	if [ -d $SARDIR ]; then
		if ls $SARDIR/sar* 1> /dev/null 2>&1; then
			printf "Sar files exist\n"
			NUMPROC=$(sed -n "s/CPU(s):[ \t]*//p" hardware.txt | awk 'NR==1{print $1}')
			printf "Number of processors: "
			echo -e "$NUMPROC\n"
		else
			echo "Sar files do not exist"
		fi
	else
		echo "Sar files do not exist"
	fi
}
