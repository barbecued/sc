# sc
A tool to display ssome useful information from a supportconfig.

Install by running install.sh as root or with sudo.
Installer places necessary files in /usr/local/bin.

Usage:

Navigate to the location of an extracted supportconfig and run "sc"

What does it do? It displays:

-Kernel version and Kernel Release Date

-Version of SLES

-Kernel Taint and what the tainting flags are

-Kernel verification with rpm -V output. No news is good news

-Number of patches needed based on what zypper sees as available. Not compared against scc.

-Architecture type

-Memory utilizaiton

-Oom-killer messages are highlighted in RED

-Boot based on syslog starting in /var/log/messages. May have many false matches, but this will give you an idea


This tool needs more. Please feel free to contribute.

