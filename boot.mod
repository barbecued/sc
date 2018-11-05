#!/bin/bash

function bootsles11()
{
	grep -nr "syslog-ng starting up" message* 
}

function bootsles12()
{
	#grep -nr "origin software" message* #SLES 12
	grep -nr "origin\ software=\"rsyslogd\"" message* #SLES 12
}

function bootall()
{
	echo "testing"
}                         



