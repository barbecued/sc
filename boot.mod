#!/bin/bash

function bootsles11()
{
grep -nr "syslog-ng starting up" message* #SLES 11
}

function bootsles12()
{
grep -nr "software=\"rsyslogd\"" message* #SLES 12
}

function bootall()
{
echo "placeholder text so this script does not fail due to function being empty"
}



