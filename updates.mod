#!/bin/bash

function neededpatchesnumber()
{
grep -nr "patches needed" update* 
}
