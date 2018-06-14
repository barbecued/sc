#!/bin/bash


#Default values for flags
verbose='false'
aflag=''
bflag=''
input=.

#Case to get flags
while getopts 'abi:v' flag; do
  case "${flag}" in
    a) aflag='true' ;;
    b) bflag='true' ;;
    i) input="${OPTARG}" ;;
    v) verbose='true' ;;
    *) error "Unexpected option ${flag}" ;;
  esac
done

#Load modules. To add additional modules, syntax is ". boot.mod" to load the script module
. boot.mod

PRETTY_NAME=$(grep -r "PRETTY_NAME" $input/basic-environment.txt)
echo $PRETTY_NAME
grep -nri -A5 "/usr/bin/free -k" $input/basic-health-check.txt

echo " "

#Boot Info

if [[ $PRETTY_NAME = *"11"* ]]; then
  bootsles11
elif [[ $PRETTY_NAME = *"12"* ]]; then
  bootsles12
else
  echo "SLES version not found / specified in PRETTY_NAME"
  bootsles11
  bootsles12
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

