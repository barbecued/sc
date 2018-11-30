#! /usr/bin/python3
# SC Python Script
# Use to help anaylize supportconfig files

import sys
import argparse
import re

parser = argparse.ArgumentParser('sc.py')
parser.add_argument('-v', action='store_true', help='print verbose output')
parser.add_argument('-p', action='store_true', help='print information about service performance (i.e. # of cpus, sar data, etc)')

args = parser.parse_args()

#Display OS
text2find = 'PRETTY_NAME'
try:
    for line in open('basic-environment.txt', 'r'):
        if re.search(text2find, line):
            print('\u001b[1mOS Version: \u001b[0m' + re.findall(r'"(.*?)"', line)[0])
except IOError:
    print('\u001b[1mOS Version: \u001b[0m Error reading basic-environment.txt')

#Display Kernel Version
try:
    with open('basic-environment.txt', 'r') as searchfile:
        for line in searchfile:
            if 'GNU/Linux' in line:
                print("\u001b[1mKernel Version: \u001b[0m" + line.split()[2])
except IOError:
    print('\u001b[1mKernel Version: \u001b[0m Error reading basic-environment.txt')

with open('basic-health-check.txt', 'r') as searchfile:
    for line in searchfile:
        if '/usr/bin/free' in line:
            print("\u001b[1mMemory: \u001b[0m \n" + next(searchfile, '').strip() + "\n" + next(searchfile, '').strip() + "\n" + next(searchfile, '').strip() + "\n" + next(searchfile, '').strip())
            
#Display Architecture
text2find = 'Linux'
try:
    for line in open('basic-environment.txt', 'r'):
        if re.search(text2find, line):
            print('\u001b[1mArchitecture: \u001b[0m' + str(line.split()[12]))
            break
except IOError:
    print('\u001b[1mArchitecture: \u001b[0m Error reading basic-environment.txt')
