#!/bin/bash

bold=$(tput bold)
normal=$(tput sgr0)
echo

#Default values for flags
verbose='false'
aflag='.txt'
bflag=''
input=.

#Case to get flags
while getopts 'abi:v' flag; do
  case "${flag}" in
    a) aflag='*' ;;
    b) bflag='true' ;;
    i) input="${OPTARG}" ;;
    v) verbose='true' ;;
    *) error "Unexpected option ${flag}" ;;
  esac
done

#Load modules. To add additional modules, syntax is ". boot.mod" to load the script module
. boot.mod
. updates.mod



#display OS version
PRETTY_NAME=$(grep -r "PRETTY_NAME" $input/basic-environment.txt)
echo -n "${bold}OS Version: ${normal}" 
echo $PRETTY_NAME | cut -d = -f2 | tr -d \"
echo

#display kernel version
KERNEL=$(grep "Linux" $input/basic-environment.txt | cut -d " " -f 3 | cut -d \- -f1,2 | grep -Ev '[A-Za-z]')
echo "${bold}Kernel Version: ${normal}" $KERNEL
echo -n "${bold}Kernel Release Date: ${normal}"
wget -qO- "https://wiki.microfocus.com/index.php?title=SUSE/SLES/Kernel_versions" | grep -B 2 $(echo $KERNEL) | grep "<th>" | cut -d " " -f 2
echo

#display architecture
ARCH=$(grep "Linux" $input/basic-environment.txt | grep -v SUSE | cut -d " " -f 13)
echo "${bold}Architecture: ${normal}" $ARCH
echo


#display memory info
echo "${bold}MEMORY ${normal}"
grep -nri -A5 "/usr/bin/free -k" $input/basic-health-check.txt
grep -nri "invoked oom-killer" messages$aflag --color=auto
echo

#display Boot Info
echo "${bold}BOOT HISTORY ${normal}"
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

#display Updates info
echo "${bold}PATCHES NEEDED ${normal}"
neededpatchesnumber



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
                                                                                                                                                      
                                                  
