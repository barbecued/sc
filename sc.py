#! /usr/bin/python3
#SC Python Script
#Use to help anaylize supportconfig files

import sys
import argparse
import re

parser = argparse.ArgumentParser('sc.py')
parser.add_argument('-v', action='store_true', help='print verbose output')
parser.add_argument('-p', action='store_true', help='print information about service performance (i.e. # of cpus, sar data, etc)')

args = parser.parse_args()

#Display OS Version
text2find = 'PRETTY_NAME'
for line in open('basic-environment.txt', 'r'):
    if re.search(text2find, line):
        print('OS Version: ' + re.findall(r'"(.*?)"', line)[0])