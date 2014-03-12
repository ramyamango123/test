import re

#lines = "# This is a sample file"
lines = "This is a sample file"

#pound_check = re.search("#\s+(\w+\s+\w+\s+\w+\s+\w+\s+\w+)", lines)
pound_check = re.search("(#.*)", lines)
#check =  "#" + " " + pound_check.group(1)

if pound_check != None:
    print "it's just a comment statement"
else:
    print "Not a comment statement"