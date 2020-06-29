#!/bin/bash


#test slack app integration

bold=$(tput bold)
normal=$(tput sgr0)

#Default values for flags
verbose='false'
aflag='.txt'
bflag=''
input=.
performance='false'

#Variables you can adjust
column1="%-20s" # spaces before column 2 starts
column2="%-20s" # spaces before column 2 starts


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

### FUNCTIONS ###
# Boot
function bootsles11() {	grep -nr "syslog-ng starting up" message* ;}
function bootsles12() { grep -nr "origin\ software=\"rsyslogd\"" message* | grep -v HUPed ;}

# Performance
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

# Updates
function neededpatchesnumber() { grep -m 1 "patches needed" updates.txt ;}

### END FUNCTIONS ###

#display Boot Info
echo
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
echo 

#display Updates info
echo "${bold}PATCHES NEEDED ${normal}"
neededpatchesnumber

#Check server performance
if [ $performance = 'true' ]; then
	cpu_load
fi

#display OS version
PRETTY_NAME=$(grep -r "PRETTY_NAME" $input/basic-environment.txt)
#echo -n "${bold}OS Version: ${normal}" 
printf  "$column1 $column2" "${bold}OS Version: ${normal}" "$PRETTY_NAME"
#echo $PRETTY_NAME | cut -d = -f2 | tr -d \"
echo

#display kernel version
KERNEL=$(grep "Linux" $input/basic-environment.txt | cut -d " " -f 3 | cut -d \- -f1,2 | grep -Ev '[A-Za-z]')
echo "${bold}Kernel Version: ${normal}" $KERNEL
echo -n "${bold}Kernel Release Date: ${normal}"
#wget -qO- "https://wiki.microfocus.com/index.php?title=SUSE/SLES/Kernel_versions" | grep -B 2 $(echo $KERNEL).1 | grep "<th>" | cut -d " " -f 2
wget -qO- "https://www.suse.com/support/kb/doc/?id=000019587" | grep -o -P ".{0,80}$KERNEL" | cut -f2 -d">" | cut -f1 -d"<"
echo -n "${bold}Kernel Verification:${normal} (no news is good news)"
#check for kernel taint
echo -n "${bold}   Kernel Taint${normal} $(grep -r "Kernel Status" basic-health-check.txt | cut -d "-" -f2-)"
grep -i -B1 'status: failed' boot.txt
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
