# match between 0-255
import re

input = "256 has 4.5.30.255 here"

octet = "([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-5][0-5]))"
res = re.search("\\b" + octet + "\\b", "fdjds34 54dfdss  fgjdfg    200? jfkjds")
if res != None:
    print res.group(1)
else:
    print "Not found"

ip_pattern = "\\b" + "(" + octet + "\." + octet + "\." + octet + "\." + \
                           octet + ")" + "\\b"
res = re.search(ip_pattern, input)

if res != None:
    print res.group(1)
else:
    print "Not found"