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

}



