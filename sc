#!/bin/bash

verbose='false'
aflag=''
bflag=''
input=.

while getopts 'abi:v' flag; do
  case "${flag}" in
    a) aflag='true' ;;
    b) bflag='true' ;;
    i) input="${OPTARG}" ;;
    v) verbose='true' ;;
    *) error "Unexpected option ${flag}" ;;
  esac
done

. boot.mod

grep -r "PRETTY_NAME" $input/basic-environment.txt
grep -nri -A5 "/usr/bin/free -k" $input/basic-health-check.txt

echo " "

#Boot Info

bootsles11

grep -r "PRETTY_NAME" $input/basic-environment.txt


read -p "  PROMPT: " prompt

if [["$prompt" = "q"]]
then
        exit
else
function memory()
{
grep -nri -A5 "/usr/bin/free -k" $input/basic-health-check.txt
}
fi
