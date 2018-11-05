#!/bin/bash

function neededpatchesnumber()
{
	grep "patches needed" updates.txt
}
