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

#### Is this script being run inside a supportconfig?  If not, exit ####
#if [[ -f ./basic-environment.txt ]]; then
#    echo "Both files exist."
#    else echo
#         echo "This directory does not appear to be an extracted supportconfig."
#         echo
#         echo "For sc to run, the file basic-environment.txt (which is one of the files contained in a supportconfig) must be found in the current working directory.  Please extract a supportconfig and cd into the supportconfig main directory before running sc again"
#         echo
#         exit 1
#fi




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

# Sanity is this a supportconfig check
function sanitycheck () {
	if [[ ! -f ./basic-environment.txt ]]; then
       		echo
        	echo "This directory does not appear to be an extracted supportconfig."
         	echo
         	echo "For sc to run, the file basic-environment.txt (which is one of the files contained in a supportconfig) must be found in the current working directory.  Please extract a supportconfig and cd into the supportconfig main directory before running sc again"
         	echo
         	exit 1
	fi
}
### END FUNCTIONS ###

#sanity check -- is the current working directory an extracted supportconfig
sanitycheck

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

#oomkiller search
grep -nri "invoked oom-killer" messages$aflag --color=auto



#Timestamp of supportconfig based on uname -a
echo "${bold}Date:           ${normal}" $(grep -A1 "\/bin\/date" basic-environment.txt | xargs | cut -d ' ' -f3-)

#display Updates info
echo "${bold}Patches Needed: ${normal}" $(neededpatchesnumber)

#Check server performance
if [ $performance = 'true' ]; then
	cpu_load
fi

#display OS version
PRETTY_NAME=$(grep -r "PRETTY_NAME" $input/basic-environment.txt | cut -f2- -d '"')
#echo -n "${bold}OS Version: ${normal}" 
printf  "$column1 $column2" "${bold}OS Version:     ${normal}" "$PRETTY_NAME" 
#echo $PRETTY_NAME | cut -d = -f2 | tr -d \"
echo

#display kernel version
KERNEL=$(grep "Linux" $input/basic-environment.txt | cut -d " " -f 3 | cut -d \- -f1,2 | grep -Ev '[A-Za-z]')
echo -n "${bold}Kernel Version: ${normal}" $KERNEL
echo -n "   ${bold}Released: ${normal}"
#wget -qO- "https://wiki.microfocus.com/index.php?title=SUSE/SLES/Kernel_versions" | grep -B 2 $(echo $KERNEL).1 | grep "<th>" | cut -d " " -f 2
wget -qO- "https://www.suse.com/support/kb/doc/?id=000019587" | grep -o -P ".{0,80}$KERNEL" | cut -f2 -d">" | cut -f1 -d"<"
#echo -n "${bold}Kernel Verification:${normal} (no news is good news)"
#check for kernel taint
echo -n "${bold}Kernel Taint   ${normal}  "
grep -r "Kernel Status" basic-health-check.txt | cut -d " " -f4-
grep -i -B1 'status: failed' boot.txt

#display architecture
ARCH=$(grep "Linux" $input/basic-environment.txt | grep -v SUSE | cut -d " " -f 13)
echo "${bold}Architecture:   ${normal}" $ARCH
echo

#display memory info
echo "${bold}MEMORY ${normal}"
grep -A5 "/usr/bin/free -k" $input/basic-health-check.txt

