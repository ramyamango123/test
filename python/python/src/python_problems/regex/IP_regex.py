# IP Address

import re

x = "366.678.786.23544"
#x = "345"

#pattern = re.search("^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$", x)
pattern = re.match("\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$", x)

#pattern = re.search("^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$",x)

p =  pattern

print p
