#!/bin/bash

function neededpatchesnumber()
{
	grep -r "patches needed" update* 
}
