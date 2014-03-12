import sys
import os

args = [os.path.realpath(os.path.expanduser("/usr/local/bin/raft")), "-f"] + sys.argv
print " ".join(args)
#print args
#print "hello"
