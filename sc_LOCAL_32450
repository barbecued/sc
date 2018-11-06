#!/bin/bash


#Default values for flags
verbose='false'
aflag='.txt'
bflag=''
input=.
performance='false'

#Case to get flags
while getopts 'abi:vp' flag; do
  case "${flag}" in
    a) aflag='*' ;;
    b) bflag='true' ;;
    i) input="${OPTARG}" ;;
    v) verbose='true' ;;
    p) performance='true' ;;
    *) error "Unexpected option ${flag}" ;;
  esac
done

#Load modules. To add additional modules, syntax is ". boot.mod" to load the script module
. boot.mod
. updates.mod

#echo OS version
PRETTY_NAME=$(grep -r "PRETTY_NAME" $input/basic-environment.txt)
echo -n "OS Version: " 
echo $PRETTY_NAME | cut -d = -f2 | tr -d \"

#echo kernel version
KERNEL=$(grep "Linux" $input/basic-environment.txt | cut -d " " -f 3 | cut -d \- -f1,2 | grep -Ev '[A-Za-z]')
echo -n "Kernel Version: "
echo $KERNEL
echo -n "Kernel Release Date: "
wget -qO- "https://wiki.microfocus.com/index.php?title=SUSE/SLES/Kernel_versions" | grep -B 2 $(echo $KERNEL) | grep "<th>" | cut -d " " -f 2

#echo architecture
ARCH=$(grep "Linux" $input/basic-environment.txt | grep -v SUSE | cut -d " " -f 13)
echo -n "Architecture: "
echo $ARCH " "

#echo kernel verification
echo "kernel verification (no news is good news)"
grep -i -B1 'status: failed' boot.txt


#echo memory
echo "MEMORY "
grep -nri -A5 "/usr/bin/free -k" $input/basic-health-check.txt
grep -nri "invoked oom-killer" messages$aflag --color=auto

echo " "

#Boot Info
echo "BOOT HISTORY "
if [[ $PRETTY_NAME = *"11"* ]]; then
  bootsles11
elif [[ $PRETTY_NAME = *"12"* ]]; then
  bootsles12
else
  echo "SLES version not found / specified in PRETTY_NAME"
  bootsles11
  bootsles12
fi

echo " "

#Updates
echo "PATCHES NEEDED "
neededpatchesnumber

#Check server performance
if [ $performance = 'true' ]; then
	. performance.mod
	cpu_load
fi

#read -p "  PROMPT: " prompt

#if [["$prompt" = "q"]]
#then
#        exit
#else
#function memory()
#{
#grep -nri -A5 "/usr/bin/free -k" $input/basic-health-check.txt
#}
#fi
                                                                                                                                                      
                                                  
