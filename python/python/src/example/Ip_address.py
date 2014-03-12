import re

x = "Ip address is an unique address - 366.678.786.235"
#y = "936.678.786.238"
pattern1 = re.search("(\\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\\b)", x)
print pattern1
#
#pattern2 = re.search("^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$", y)
#
print "pattern1: ", pattern1
#print "pattern2: ", pattern2

#p = re.match("\\bthe\\b", "the")
#print p