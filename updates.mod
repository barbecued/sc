#!/bin/bash

function neededpatchesnumber()
{
	grep -m 1 "patches needed" updates.txt
}
